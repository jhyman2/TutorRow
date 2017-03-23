import fetch_unis_saga                    from './fetch_unis_saga';
import user_post_saga                     from './user_post_saga';
import fetch_all_courses_saga             from './fetch_all_uni_courses_saga';
import fetch_selected_course_saga         from './fetch_selected_course_saga';
import fetch_students_for_course_saga     from './fetch_students_for_course_saga';
import fetch_tutors_for_course_saga       from './fetch_tutors_for_course_saga';
import sign_up_studenting_for_course_saga from './sign_up_studenting_for_course';
import sign_up_tutoring_for_course_saga   from './sign_up_tutoring_for_course';
import cancel_studenting_for_course_saga  from './cancel_studenting_for_course';
import cancel_tutoring_for_course_saga    from './cancel_tutoring_for_course';

export default function* sagas () {
  yield [
    fetch_unis_saga(),
    user_post_saga(),
    fetch_all_courses_saga(),
    fetch_selected_course_saga(),
    fetch_students_for_course_saga(),
    fetch_tutors_for_course_saga(),
    sign_up_studenting_for_course_saga(),
    sign_up_tutoring_for_course_saga(),
    cancel_studenting_for_course_saga(),
    cancel_tutoring_for_course_saga()
  ];
}