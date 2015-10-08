 

CREATE TABLE developers (
    id serial NOT NULL,
    key character varying,
    email character varying,
    username character varying,
    password character varying,
    active boolean DEFAULT false,
    endpoint character varying,
    certurl character varying
);

 

 
CREATE TABLE fences (
    id serial NOT NULL,
    poligono geometry,
    nombre character varying,
    radio real,
    ibeacon character varying,
    latcentro double precision,
    longcentro double precision,
    developerid integer
);


 

CREATE TABLE reglas (
    id serial NOT NULL,
    fenceid integer,
    statusfence integer,
    developerkey character varying,
    desde date,
    hasta date,
    data character varying,
    destinatario integer DEFAULT 1,
    periodfence integer DEFAULT 1,
    tohour integer DEFAULT 24,
    fromhour integer DEFAULT 0
);


 

CREATE TABLE users (
    id serial NOT NULL,
    user_id character varying,
    dev_id integer
);

 

 
CREATE TABLE users_rules (
    user_id integer NOT NULL,
    rule_id integer NOT NULL
);

 
  
ALTER TABLE ONLY fences
    ADD CONSTRAINT "PK circulos" PRIMARY KEY (id);


ALTER TABLE ONLY users
    ADD CONSTRAINT "PK user" PRIMARY KEY (id);



ALTER TABLE ONLY users_rules
    ADD CONSTRAINT "PK users_rules" PRIMARY KEY (user_id, rule_id);



ALTER TABLE ONLY developers
    ADD CONSTRAINT developers_pkey PRIMARY KEY (id);



ALTER TABLE ONLY reglas
    ADD CONSTRAINT rules_pk PRIMARY KEY (id);


ALTER TABLE ONLY users
    ADD CONSTRAINT "FK Developer" FOREIGN KEY (dev_id) REFERENCES developers(id);




ALTER TABLE ONLY users_rules
    ADD CONSTRAINT "FK rule" FOREIGN KEY (rule_id) REFERENCES reglas(id) ON DELETE CASCADE;



ALTER TABLE ONLY users_rules
    ADD CONSTRAINT "FK user" FOREIGN KEY (user_id) REFERENCES users(id);


ALTER TABLE ONLY fences
    ADD CONSTRAINT "developer fk" FOREIGN KEY (developerid) REFERENCES developers(id);


ALTER TABLE ONLY reglas
    ADD CONSTRAINT fences_fk FOREIGN KEY (fenceid) REFERENCES fences(id) ON DELETE CASCADE;
    
    
CREATE TABLE envios
(
  id serial NOT NULL,
  fenceid integer NOT NULL,
  userid character varying NOT NULL,
  fecha timestamp with time zone,
  CONSTRAINT "pk envios" PRIMARY KEY (id),
  CONSTRAINT "fk fences" FOREIGN KEY (fenceid)
      REFERENCES fences (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);

 
