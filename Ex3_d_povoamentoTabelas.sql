
--3d ) Povoamento dmTempo (codigo, data, dia, mes, trimestre, semestre, ano) com data distintas;
CREATE SEQUENCE sequencia_dm_Tempo START WITH 1 ORDER;

DECLARE  
	CURSOR cursorPed IS 
		SELECT DISTINCT dtPedido FROM Pedido; --Obter somente datas distintas de pedidos. 
  
BEGIN  
	FOR linhaPed IN cursorPed LOOP  
		INSERT INTO dmTempo(codigo, data, dia, mes, ano, trimestre, semestre)
           VALUES(  sequencia_dm_Tempo.NEXTVAL,
                    linhaPed.dtPedido,
                    EXTRACT(day from linhaPed.dtPedido),
                    EXTRACT(month from linhaPed.dtPedido),
                    EXTRACT(year from linhaPed.dtPedido),
                    func_Dt_Trimestre(linhaPed.dtPedido), 
                    func_Dt_Semestre(linhaPed.dtPedido)
                 );
	END LOOP;
END;


--3d ) Povoamento  dmProduto(codigo, nome, nomeCategoria, preco) com produtos distintos vendidos. 
DECLARE
  CURSOR cursorProd IS
    SELECT DISTINCT Prod.codigo, Prod.nome, Prod.preco, Cat.nome as nomeCategoria
      FROM Produto Prod, DetalhesPedido DP, Categoria Cat
      WHERE Prod.codigo = DP.codigoProduto AND 
            Prod.codigoCategoria = Cat.codigo;
      
BEGIN
  FOR linhaProdDP IN cursorProd LOOP
    INSERT INTO dmProduto(codigo, nome, nomeCategoria, preco)
           VALUES( linhaProdDP.codigo, linhaProdDP.nome, linhaProdDP.nomeCategoria, linhaProdDP.preco);
  END LOOP;
END;


--3d ) Povoamento  dmRegiao(codigo, cidade, estado, pais, continente) com regiões(cidade, estado e pais) distintas. 
CREATE SEQUENCE sequencia_dmRegiao START WITH 1 ORDER;

CREATE OR REPLACE FUNCTION getContinente(pais IN endereco.pais%TYPE) 
  RETURN dmRegiao.continente%TYPE
  IS
  BEGIN
    CASE
      WHEN pais = 'France' THEN RETURN 'Europa';
      WHEN pais = 'Germany' THEN RETURN 'Europa';
      WHEN pais = 'United Kingdom' THEN RETURN 'Europa';
      WHEN pais = 'Australia' THEN RETURN 'Oceania';
      ELSE RETURN 'America';
    END CASE;
END getContinente;

DECLARE CURSOR C_dmRegiao IS
  SELECT DISTINCT En.cidade, En.estado, En.pais
  FROM (  
        SELECT DISTINCT Ped.enderecoentrega
        FROM pedido Ped, endereco En
        WHERE Ped.enderecoentrega = En.id
       )PedEn, Endereco En
  WHERE PedEn.enderecoentrega = En.id;

  
BEGIN
  FOR linhaPed IN C_dmRegiao 
  LOOP
    INSERT INTO dmRegiao(codigo, cidade, estado, pais, continente)
    VALUES(sequencia_dmRegiao.NEXTVAL, linhaPed.cidade, linhaPed.estado, 
           linhaPed.pais, getContinente(linhaPed.pais) );
  END LOOP;  
END;


--3 Povoamento ftVenda (codigoProduto, codigoTempo, codigoRegiao, quantidade, precoUnitario)
DECLARE CURSOR C_ftVenda IS
  SELECT codigoproduto, quantidade, precounitario, codigoTempo, dmRegiao.codigo as codigoRegiao 
  FROM ( 
         SELECT codigoproduto, quantidade, precounitario, cidade, estado, pais, dmTempo.codigo as codigoTempo, count(*)
         FROM (
                 SELECT DISTINCT codigoproduto, quantidade, precounitario, dtpedido, cidade, estado, pais
                 FROM (
                        SELECT DISTINCT codigoproduto, quantidade, precounitario, dtpedido, enderecoentrega
                        FROM detalhespedido Det, Pedido Ped
                        WHERE Det.codigopedido = Ped.codigo
                       ) DetPed, Endereco EN
                 WHERE DetPed.enderecoEntrega = En.id
               ) DetPedEn, dmTempo
         WHERE DetPedEn.dtPedido = dmTempo.data
         group by codigoproduto, quantidade, precounitario, cidade, estado, pais, dmTempo.codigo
        ) DetPedEnTempo, dmRegiao
  WHERE DetPedEnTempo.cidade = dmRegiao.cidade AND DetPedEnTempo.estado = dmRegiao.estado AND DetPedEnTempo.pais = dmRegiao.pais;
  
BEGIN
  FOR linhaPed IN C_ftVenda
  LOOP
    INSERT INTO ftVenda (codigoProduto, codigoTempo, codigoRegiao, quantidade, precoUnitario)
    VALUES(linhaPed.codigoProduto, linhaPed.codigoTempo, linhaPed.codigoRegiao, 
           linhaPed.quantidade,linhaPed.precoUnitario);
  END LOOP;  
END;





