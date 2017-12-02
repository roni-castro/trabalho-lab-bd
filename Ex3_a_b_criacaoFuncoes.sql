--3a) Crie uma função chamada funcDtTrimestre que receba como parâmetro de entrada uma data e
--    retorne um valor numérico [1, 4] indicando o trimestre da data de entrada.

CREATE OR REPLACE FUNCTION func_Dt_Trimestre (data_de_entrada IN dmTempo.data%TYPE)
  RETURN NUMBER IS
  mes dmTempo.mes%TYPE := EXTRACT(month FROM data_de_entrada);
  
  BEGIN
  CASE
    WHEN mes BETWEEN 1 AND 3 THEN RETURN 1;
    WHEN mes BETWEEN 4 AND 6 THEN RETURN 2;
    WHEN mes BETWEEN 7 AND 9 THEN RETURN 3;
    ELSE RETURN 4;
  END CASE;
  
END func_Dt_Trimestre;

--3b) Crie uma função chamada funcDtSemestre que receba como parâmetro de entrada uma data e retorne
--    um valor numérico [1, 2] indicando o semestre da data de entrada
CREATE OR REPLACE FUNCTION func_Dt_Semestre (data_de_entrada IN dmTempo.data%TYPE)
  RETURN NUMBER IS
  mes dmTempo.mes%TYPE := EXTRACT(month FROM data_de_entrada);
  
  BEGIN
  IF mes BETWEEN 1 AND 6 THEN 
    RETURN 1;
  ELSE
    RETURN 2;
  END IF;
  
END func_Dt_Semestre;

