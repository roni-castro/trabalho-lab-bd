

--Granularidades de Produto: "todos_os_produtos" ou "cada_produto"
--Granularidades de Região: "cidade", "estado", "pais", "continente"
CREATE OR REPLACE PROCEDURE Vendas_ProdutoXRegiao(
  granProduto IN VARCHAR2, granRegiao IN VARCHAR2,
  diaInicio IN NUMBER, mesInicio IN NUMBER, anoInicio IN NUMBER,
  diaFim IN NUMBER,mesFim IN NUMBER,anoFim IN NUMBER,  p_recordset OUT SYS_REFCURSOR) 
IS 
    comando varchar2(4000);

  BEGIN
    IF granProduto = 'todos_os_produtos' THEN
     comando := 
          'SELECT dmR.'||granRegiao ||','|| 'SUM(ftV.precoUnitario*ftV.quantidade) AS Faturamento_Total
          FROM dmTempo dmT, dmProduto dmP, dmRegiao dmR, ftVenda ftV
          WHERE pertence_ao_Intervalo('|| diaInicio ||','|| mesInicio ||','|| anoInicio ||', '||
            diaFim ||','|| mesFim ||','|| anoFim ||','|| 'dmT.dia, dmT.mes, dmT.ano) = 1
            AND dmT.codigo = ftV.codigoTempo
            AND dmP.codigo = ftV.codigoProduto
            AND dmR.codigo = ftV.codigoRegiao
          GROUP BY dmR.'|| granRegiao ||
          ' ORDER BY dmR.'|| granRegiao;
    ELSE 
     comando := 
          'SELECT dmP.codigo, dmP.nome, '|| 'dmR.'||granRegiao ||','|| 'SUM(ftV.precoUnitario*ftV.quantidade) AS Faturamento_Total
          FROM dmTempo dmT, dmProduto dmP, dmRegiao dmR, ftVenda ftV
          WHERE pertence_ao_Intervalo('|| diaInicio ||','|| mesInicio ||','|| anoInicio ||', '||
            diaFim ||','|| mesFim ||','|| anoFim ||','|| 'dmT.dia, dmT.mes, dmT.ano) = 1
            AND dmT.codigo = ftV.codigoTempo
            AND dmP.codigo = ftV.codigoProduto
            AND dmR.codigo = ftV.codigoRegiao
          GROUP BY dmP.codigo, dmP.nome, '|| 'dmR.'|| granRegiao ||
          ' ORDER BY dmP.codigo,'|| 'dmR.'|| granRegiao;    
    END IF;
    OPEN p_recordset FOR comando;
END Vendas_ProdutoXRegiao;

SET SERVEROUTPUT ON;
DECLARE
  cursorPedido  SYS_REFCURSOR;
  codigo_produto VARCHAR2(50); 
  nome_produto  VARCHAR2(50);
  pais  VARCHAR2(50);
  faturamento_total VARCHAR2(50);

BEGIN
  Vendas_ProdutoXRegiao('cada_produto', 'pais', 1, 1, 2007, 3, 6, 2015, cursorPedido);
            
  LOOP 
    FETCH cursorPedido
    INTO  codigo_produto, nome_produto, pais, faturamento_total;
    EXIT WHEN cursorPedido%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(codigo_produto || '|' || nome_produto || ' | ' || pais|| ' | ' ||faturamento_total);
  END LOOP;
  CLOSE cursorPedido;
END;