--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6
-- Dumped by pg_dump version 10.5

-- Started on 2018-11-16 16:04:01

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 2172
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 1 (class 3079 OID 12278)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 16384)
-- Name: User; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."User" (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public."User" OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16414)
-- Name: answer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.answer (
    id integer NOT NULL,
    content character varying(50) NOT NULL
);


ALTER TABLE public.answer OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16409)
-- Name: question; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.question (
    id integer NOT NULL,
    content character varying(50) NOT NULL
);


ALTER TABLE public.question OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 16467)
-- Name: relations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.relations (
    id_user integer NOT NULL,
    id_question integer,
    id_answer integer
);


ALTER TABLE public.relations OWNER TO postgres;

--
-- TOC entry 2035 (class 2606 OID 16388)
-- Name: User User_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);


--
-- TOC entry 2039 (class 2606 OID 16418)
-- Name: answer answer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (id);


--
-- TOC entry 2037 (class 2606 OID 16413)
-- Name: question question_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.question
    ADD CONSTRAINT question_pkey PRIMARY KEY (id);


--
-- TOC entry 2041 (class 2606 OID 16471)
-- Name: relations relations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.relations
    ADD CONSTRAINT relations_pkey PRIMARY KEY (id_user);


--
-- TOC entry 2045 (class 2606 OID 16482)
-- Name: relations answer_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.relations
    ADD CONSTRAINT answer_fkey FOREIGN KEY (id_answer) REFERENCES public.answer(id);


--
-- TOC entry 2043 (class 2606 OID 16472)
-- Name: relations question_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.relations
    ADD CONSTRAINT question_fkey FOREIGN KEY (id_question) REFERENCES public.question(id);


--
-- TOC entry 2042 (class 2606 OID 16404)
-- Name: User user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT user_fkey FOREIGN KEY (id) REFERENCES public."User"(id);


--
-- TOC entry 2044 (class 2606 OID 16477)
-- Name: relations user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.relations
    ADD CONSTRAINT user_fkey FOREIGN KEY (id_user) REFERENCES public."User"(id);


-- Completed on 2018-11-16 16:04:01

--
-- PostgreSQL database dump complete
--

