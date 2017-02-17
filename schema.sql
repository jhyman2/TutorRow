--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

-- Started on 2017-02-16 22:49:18 PST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12655)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2415 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 192 (class 1259 OID 16434)
-- Name: course_instances; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE course_instances (
    id integer NOT NULL,
    student_id smallint NOT NULL,
    course_id smallint NOT NULL,
    role character(255) NOT NULL
);


ALTER TABLE course_instances OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16432)
-- Name: course_instances_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE course_instances_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE course_instances_id_seq OWNER TO postgres;

--
-- TOC entry 2416 (class 0 OID 0)
-- Dependencies: 191
-- Name: course_instances_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE course_instances_id_seq OWNED BY course_instances.id;


--
-- TOC entry 190 (class 1259 OID 16425)
-- Name: courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE courses (
    id integer NOT NULL,
    name character(255),
    department character(255) NOT NULL,
    course_num smallint NOT NULL,
    professor character(255),
    description character(255),
    num_credits smallint,
    university_id smallint NOT NULL
);


ALTER TABLE courses OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16423)
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE courses_id_seq OWNER TO postgres;

--
-- TOC entry 2417 (class 0 OID 0)
-- Dependencies: 189
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE courses_id_seq OWNED BY courses.id;


--
-- TOC entry 187 (class 1259 OID 16408)
-- Name: universities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE universities (
    name character(255) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE universities OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16414)
-- Name: universities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE universities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE universities_id_seq OWNER TO postgres;

--
-- TOC entry 2418 (class 0 OID 0)
-- Dependencies: 188
-- Name: universities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE universities_id_seq OWNED BY universities.id;


--
-- TOC entry 185 (class 1259 OID 16396)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id integer NOT NULL,
    university_id smallint,
    full_name character(255) NOT NULL,
    facebook_id bigint
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16399)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- TOC entry 2419 (class 0 OID 0)
-- Dependencies: 186
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 2291 (class 2604 OID 16437)
-- Name: course_instances id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY course_instances ALTER COLUMN id SET DEFAULT nextval('course_instances_id_seq'::regclass);


--
-- TOC entry 2290 (class 2604 OID 16428)
-- Name: courses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY courses ALTER COLUMN id SET DEFAULT nextval('courses_id_seq'::regclass);


--
-- TOC entry 2289 (class 2604 OID 16416)
-- Name: universities id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY universities ALTER COLUMN id SET DEFAULT nextval('universities_id_seq'::regclass);


--
-- TOC entry 2288 (class 2604 OID 16401)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


-- Completed on 2017-02-16 22:49:19 PST

--
-- PostgreSQL database dump complete
--

