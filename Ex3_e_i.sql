CREATE OR REPLACE PROCEDURE Vendas_RegiaoXTempo(
  granRegiao IN VARCHAR2, granTempo IN VARCHAR2,
  diaInicio IN NUMBER, mesInicio IN NUMBER, anoInicio IN NUMBER,
  diaFim IN NUMBER,mesFim IN NUMBER,anoFim IN NUMBER,  cursor_Referencia OUT SYS_REFCURSOR) 
IS
    comando varchar2(2500);
 
  BEGIN
     comando := 
          'SELECT dmT.'||granTempo ||','|| 'dmR.'||granRegiao ||','|| 'SUM(ftV.precoUnitario*ftV.quantidade) AS Faturamento_Total
          FROM dmTempo dmT, dmProduto dmP, dmRegiao dmR, ftVenda ftV
          WHERE pertence_ao_Intervalo('|| diaInicio ||','|| mesInicio ||','|| anoInicio ||', '||
            diaFim ||','|| mesFim ||','|| anoFim ||','|| 'dmT.dia, dmT.mes, dmT.ano) = 1
            AND dmT.codigo = ftV.codigoTempo
            AND dmP.codigo = ftV.codigoProduto
            AND dmR.codigo = ftV.codigoRegiao
          GROUP BY dmT.'|| granTempo ||','|| 'dmR.'|| granRegiao ||
          ' ORDER BY dmT.'|| granTempo ||','|| 'dmR.'|| granRegiao;
 
     OPEN cursor_Referencia FOR comando;
END Vendas_RegiaoXTempo;
 
 
 
SET SERVEROUTPUT ON;
DECLARE
  cursorPedido  SYS_REFCURSOR;
  dia  VARCHAR2(50);
  cidade  VARCHAR2(50);
  faturamento_total VARCHAR2(50);
 
BEGIN
   
  Vendas_RegiaoXTempo('pais', 'ano', 1, 11, 2007, 1, 11, 2008, cursorPedido);
             
  LOOP 
    FETCH cursorPedido
    INTO  dia, cidade, faturamento_total;
    EXIT WHEN cursorPedido%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(dia || ' | ' || cidade|| ' | ' ||faturamento_total);
  END LOOP;
  CLOSE cursorPedido;
END;