-- 3c
CREATE TABLE dmTempo(
  codigo NUMBER,
  data timestamp,
  dia NUMBER,
  mes NUMBER,
  ano NUMBER,
  trimestre NUMBER,
  semestre NUMBER,
  
  CONSTRAINT pk_dmTempo PRIMARY KEY (codigo)
);


CREATE TABLE dmProduto (
  codigo VARCHAR2(15),
  nome VARCHAR2(50),
  nomeCategoria VARCHAR2(50),
  preco NUMBER,
  
  CONSTRAINT pk_dmProduto PRIMARY KEY (codigo),
  CONSTRAINT fk_dmProduto FOREIGN KEY (codigo) REFERENCES Produto(codigo)
);


CREATE TABLE dmRegiao (
  codigo NUMBER,
  cidade VARCHAR2(30),
  estado VARCHAR2(50),
  pais VARCHAR2(50),
  continente VARCHAR2(7),
  
  CONSTRAINT pk_dmRegiao PRIMARY KEY (codigo)
);


CREATE TABLE ftVenda (
  codigoProduto VARCHAR2(15),
  codigoTempo NUMBER,
  codigoRegiao NUMBER,
  quantidade NUMBER, 
  precoUnitario NUMBER,
  
  CONSTRAINT pk_ftVenda PRIMARY KEY (codigoProduto,codigoTempo,codigoRegiao,quantidade,precoUnitario),
  
  CONSTRAINT fk_ftVenda_1 FOREIGN KEY (codigoProduto) REFERENCES dmProduto(codigo),
  CONSTRAINT fk_ftVenda_2 FOREIGN KEY (codigoTempo) REFERENCES dmTempo(codigo),
  CONSTRAINT fk_ftVenda_3 FOREIGN KEY (codigoRegiao) REFERENCES dmRegiao(codigo)
);