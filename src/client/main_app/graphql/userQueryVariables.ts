import gql from 'graphql-tag';

export default {
  query: gql`
    query user($id: Int!) {
      student(id: $id) {
        courses {
          id
        }
        id
        full_name
        university {
          id
          name
        }
      }
    },
  `,
  // @ts-ignore
  variables: { id: window.__APP_INITIAL_STATE__.user.id }
}
