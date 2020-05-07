import { gql } from 'apollo-server';

const typeDefs =  gql`
  type University {
    id: Int
    courses: [Course]
    name: String
  }

  type Student {
    id: Int
    university: University
    full_name: String
    courses: [Course]
  }

  type Course {
    id: Int
    course_num: Int
    department: String
    description: String
    name: String
    num_credits: Int
    professor: String
    students: [Student]!
    tutors: [Student]!
    university_id: Int
  }

  type Mutation {
    signupForCourse(id: Int, role: String): Course
    dropCourse(id: Int): Course
  }

  type Query {
    course(id: Int): Course
    student(id: Int): [Student]
    universities: [University]
    university(id: Int): University
  }
`;

export default typeDefs;
