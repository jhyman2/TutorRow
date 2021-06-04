PGDMP     .                    y            tutorrow    12.1    12.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16386    tutorrow    DATABASE     z   CREATE DATABASE tutorrow WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE tutorrow;
                postgres    false            �            1259    16387    course_instances    TABLE     �   CREATE TABLE public.course_instances (
    id integer NOT NULL,
    student_id smallint NOT NULL,
    course_id smallint NOT NULL,
    role character(255) NOT NULL
);
 $   DROP TABLE public.course_instances;
       public         heap    postgres    false            �            1259    16390    course_instances_id_seq    SEQUENCE     �   CREATE SEQUENCE public.course_instances_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.course_instances_id_seq;
       public          postgres    false    202            �           0    0    course_instances_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.course_instances_id_seq OWNED BY public.course_instances.id;
          public          postgres    false    203            �            1259    16392    courses    TABLE       CREATE TABLE public.courses (
    id integer NOT NULL,
    name character(255),
    department character(255) NOT NULL,
    course_num smallint NOT NULL,
    professor character(255),
    description character(255),
    num_credits smallint,
    university_id smallint NOT NULL
);
    DROP TABLE public.courses;
       public         heap    postgres    false            �            1259    16398    courses_id_seq    SEQUENCE     w   CREATE SEQUENCE public.courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.courses_id_seq;
       public          postgres    false    204            �           0    0    courses_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.courses_id_seq OWNED BY public.courses.id;
          public          postgres    false    205            �            1259    16400    universities    TABLE     `   CREATE TABLE public.universities (
    name character(255) NOT NULL,
    id integer NOT NULL
);
     DROP TABLE public.universities;
       public         heap    postgres    false            �            1259    16403    universities_id_seq    SEQUENCE     |   CREATE SEQUENCE public.universities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.universities_id_seq;
       public          postgres    false    206            �           0    0    universities_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.universities_id_seq OWNED BY public.universities.id;
          public          postgres    false    207            �            1259    16405    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    university_id smallint,
    full_name character(255) NOT NULL,
    facebook_id bigint
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16408    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    208            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    209                       2604    16410    course_instances id    DEFAULT     z   ALTER TABLE ONLY public.course_instances ALTER COLUMN id SET DEFAULT nextval('public.course_instances_id_seq'::regclass);
 B   ALTER TABLE public.course_instances ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202                       2604    16411 
   courses id    DEFAULT     h   ALTER TABLE ONLY public.courses ALTER COLUMN id SET DEFAULT nextval('public.courses_id_seq'::regclass);
 9   ALTER TABLE public.courses ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    204                       2604    16412    universities id    DEFAULT     r   ALTER TABLE ONLY public.universities ALTER COLUMN id SET DEFAULT nextval('public.universities_id_seq'::regclass);
 >   ALTER TABLE public.universities ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    207    206            	           2604    16413    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    208            �          0    16387    course_instances 
   TABLE DATA           K   COPY public.course_instances (id, student_id, course_id, role) FROM stdin;
    public          postgres    false    202   �       �          0    16392    courses 
   TABLE DATA           w   COPY public.courses (id, name, department, course_num, professor, description, num_credits, university_id) FROM stdin;
    public          postgres    false    204   �       �          0    16400    universities 
   TABLE DATA           0   COPY public.universities (name, id) FROM stdin;
    public          postgres    false    206   U       �          0    16405    users 
   TABLE DATA           J   COPY public.users (id, university_id, full_name, facebook_id) FROM stdin;
    public          postgres    false    208   |       �           0    0    course_instances_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.course_instances_id_seq', 1, false);
          public          postgres    false    203            �           0    0    courses_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.courses_id_seq', 1, true);
          public          postgres    false    205            �           0    0    universities_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.universities_id_seq', 1, true);
          public          postgres    false    207            �           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 1, true);
          public          postgres    false    209            �      x�3�4��ҔԼ�
�b���� ��#h      �   l   x�3�O����KW(�WH�I,.VY�3���{�1������ӥHO�=�('3y��3 ��#���ˁY�2��*��#E!?M�<5�$#�h��J�i�i����� s��h      �      x��urV��Ӑ+F��� �X!t      �   4   x�3�4��J��,��S��M�Sq�����������������̜+F��� ,p'�     