create or replace FUNCTION pertence_ao_Intervalo(diaInicio IN NUMBER, mesInicio IN NUMBER, anoInicio IN NUMBER, 
  diaFim IN NUMBER, mesFim IN NUMBER, anoFim IN NUMBER, dia IN NUMBER, mes IN NUMBER, ano IN NUMBER)
  RETURN NUMBER
  IS
    BEGIN
      DECLARE
        dataInicio CHAR(10);
        dataFim CHAR(10);
        data_Tab CHAR(10);
        intervalo_1 NUMBER;
        intervalo_2 NUMBER;
      BEGIN
        dataInicio := diaInicio||'/'||mesInicio||'/'||anoInicio;
        dataFim := diaFim||'/'||mesFim||'/'||anoFim;
        data_Tab := dia||'/'||mes||'/'||ano;
        IF((ano < anoInicio) OR (ano = anoInicio AND mes < mesInicio) 
            OR (ano = anoInicio AND mes = mesInicio AND dia < diaInicio)) 
            THEN
              RETURN -1;
          ELSE
            intervalo_1 := to_date(dataFim,'DD/MM/YY') - to_date(dataInicio,'DD/MM/YY');
            intervalo_2 := to_date(data_Tab,'DD/MM/YY') - to_date(dataInicio,'DD/MM/YY');
            IF intervalo_1 >= intervalo_2 THEN RETURN 1;
              ELSE RETURN -1;
            END IF;
        END IF;
      END;
END pertence_ao_Intervalo;