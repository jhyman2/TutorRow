--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.5

-- Started on 2021-02-11 23:53:20 EST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 24621)
-- Name: course_instances; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course_instances (
    id integer NOT NULL,
    student_id smallint NOT NULL,
    course_id smallint NOT NULL,
    role character varying(255) NOT NULL
);


ALTER TABLE public.course_instances OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 24624)
-- Name: course_instances_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.course_instances_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.course_instances_id_seq OWNER TO postgres;

--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 197
-- Name: course_instances_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.course_instances_id_seq OWNED BY public.course_instances.id;


--
-- TOC entry 198 (class 1259 OID 24626)
-- Name: courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.courses (
    id integer NOT NULL,
    name character varying(255),
    department character varying(255) NOT NULL,
    course_num smallint NOT NULL,
    professor character varying(255),
    description character varying(255),
    num_credits smallint,
    university_id smallint NOT NULL
);


ALTER TABLE public.courses OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 24632)
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.courses_id_seq OWNER TO postgres;

--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 199
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.courses_id_seq OWNED BY public.courses.id;


--
-- TOC entry 200 (class 1259 OID 24634)
-- Name: universities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.universities (
    name character varying(255) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.universities OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 24637)
-- Name: universities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.universities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.universities_id_seq OWNER TO postgres;

--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 201
-- Name: universities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.universities_id_seq OWNED BY public.universities.id;


--
-- TOC entry 202 (class 1259 OID 24639)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    university_id smallint,
    full_name character varying(255) NOT NULL,
    facebook_id bigint,
    email character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 24642)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 203
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3007 (class 2604 OID 24644)
-- Name: course_instances id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_instances ALTER COLUMN id SET DEFAULT nextval('public.course_instances_id_seq'::regclass);


--
-- TOC entry 3008 (class 2604 OID 24645)
-- Name: courses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses ALTER COLUMN id SET DEFAULT nextval('public.courses_id_seq'::regclass);


--
-- TOC entry 3009 (class 2604 OID 24646)
-- Name: universities id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.universities ALTER COLUMN id SET DEFAULT nextval('public.universities_id_seq'::regclass);


--
-- TOC entry 3010 (class 2604 OID 24647)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3132 (class 0 OID 24621)
-- Dependencies: 196
-- Data for Name: course_instances; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course_instances (id, student_id, course_id, role) FROM stdin;
\.


--
-- TOC entry 3134 (class 0 OID 24626)
-- Dependencies: 198
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.courses (id, name, department, course_num, professor, description, num_credits, university_id) FROM stdin;
1	Walking to class	WALK	201	Dr. Garlic	How to walk to class in any kind of weather	3	1
\.


--
-- TOC entry 3136 (class 0 OID 24634)
-- Dependencies: 200
-- Data for Name: universities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.universities (name, id) FROM stdin;
UMBC	1
UMD	2
\.


--
-- TOC entry 3138 (class 0 OID 24639)
-- Dependencies: 202
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, university_id, full_name, facebook_id, email) FROM stdin;
1	1	Jamison Hyman	10207996522450567	email_value
6	1	Philip Rashad	105722721099223	kronflip@yahoo.com
\.


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 197
-- Name: course_instances_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_instances_id_seq', 22, true);


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 199
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_id_seq', 1, true);


--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 201
-- Name: universities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.universities_id_seq', 1, true);


--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 203
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


-- Completed on 2021-02-11 23:53:21 EST

--
-- PostgreSQL database dump complete
--

