PGDMP         5                 z         
   orcamentos    13.4    13.4     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    33167 
   orcamentos    DATABASE     j   CREATE DATABASE orcamentos WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE orcamentos;
                admin    false            ?            1259    33168    despesa    TABLE     ?   CREATE TABLE public.despesa (
    id bigint NOT NULL,
    mes integer NOT NULL,
    descricao character varying(100),
    datadespesa character varying(15),
    situacao character varying(100),
    valor real,
    idorcamento bigint NOT NULL
);
    DROP TABLE public.despesa;
       public         heap    admin    false            ?            1259    33171 	   orcamento    TABLE     e   CREATE TABLE public.orcamento (
    id bigint NOT NULL,
    titulo character varying(40) NOT NULL
);
    DROP TABLE public.orcamento;
       public         heap    admin    false            ?            1259    33174    receita    TABLE     ?   CREATE TABLE public.receita (
    mes integer NOT NULL,
    tipo character varying(100) NOT NULL,
    valor real,
    idorcamento bigint NOT NULL
);
    DROP TABLE public.receita;
       public         heap    admin    false            ?            1259    33197    seqorcamento    SEQUENCE     u   CREATE SEQUENCE public.seqorcamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.seqorcamento;
       public          admin    false            ?          0    33168    despesa 
   TABLE DATA           `   COPY public.despesa (id, mes, descricao, datadespesa, situacao, valor, idorcamento) FROM stdin;
    public          admin    false    200   Q       ?          0    33171 	   orcamento 
   TABLE DATA           /   COPY public.orcamento (id, titulo) FROM stdin;
    public          admin    false    201   n       ?          0    33174    receita 
   TABLE DATA           @   COPY public.receita (mes, tipo, valor, idorcamento) FROM stdin;
    public          admin    false    202   ?       ?           0    0    seqorcamento    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.seqorcamento', 1, false);
          public          admin    false    203            -           2606    33180    orcamento orcamento_titulo_key 
   CONSTRAINT     [   ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_titulo_key UNIQUE (titulo);
 H   ALTER TABLE ONLY public.orcamento DROP CONSTRAINT orcamento_titulo_key;
       public            admin    false    201            +           2606    33182    despesa pk_despesa 
   CONSTRAINT     b   ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT pk_despesa PRIMARY KEY (id, idorcamento, mes);
 <   ALTER TABLE ONLY public.despesa DROP CONSTRAINT pk_despesa;
       public            admin    false    200    200    200            1           2606    33184    receita pk_receita 
   CONSTRAINT     d   ALTER TABLE ONLY public.receita
    ADD CONSTRAINT pk_receita PRIMARY KEY (mes, tipo, idorcamento);
 <   ALTER TABLE ONLY public.receita DROP CONSTRAINT pk_receita;
       public            admin    false    202    202    202            /           2606    33186    orcamento pk_usuario 
   CONSTRAINT     R   ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.orcamento DROP CONSTRAINT pk_usuario;
       public            admin    false    201            2           2606    33187    despesa fk_orcamento    FK CONSTRAINT     ?   ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT fk_orcamento FOREIGN KEY (idorcamento) REFERENCES public.orcamento(id) ON UPDATE CASCADE ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.despesa DROP CONSTRAINT fk_orcamento;
       public          admin    false    201    2863    200            3           2606    33192    receita fk_orcamento    FK CONSTRAINT     ?   ALTER TABLE ONLY public.receita
    ADD CONSTRAINT fk_orcamento FOREIGN KEY (idorcamento) REFERENCES public.orcamento(id) ON UPDATE CASCADE ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.receita DROP CONSTRAINT fk_orcamento;
       public          admin    false    202    201    2863            ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?     