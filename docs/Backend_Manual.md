\# 📘 Backend API Manual (Methods Guide)



\*\*Class:\*\* `DatabaseHelper`

\*\*Status:\*\* 🚧 Under Development / Planning Phase



This document lists the methods responsible for the communication between the User Interface (UI) and the Database. Team members developing the UI should design forms and actions based on the parameters defined below.



---



\## 1. Student Operations



Methods used for the student table and academic transactions related to students.



| Method Name | Parameters | Return Type | Description |

| :--- | :--- | :--- | :--- |

| \*\*CRUD \& Filtering\*\* | | | |

| `addStudent` | `(int id, String name, String surname, String dept, double gpa, ...)` | `boolean` | Creates a new student record in the database. |

| `deleteStudent` | `(int student\_id)` | `boolean` | Deletes the student and their related rows (enrollments, grades) from other tables. |

| `updateStudent` | `(int id, String name, String surname, String dept, double gpa, ...)` | `boolean` | Updates the information of an existing student. |

| `studentFilter` | `(String name, String surname, int st\_id, String dept, double gpa\_min, double gpa\_max, String sortBy)` | `ResultSet` | Retrieves a list of students based on the specified conditions and sorting order. |

| \*\*Information Retrieval\*\* | | | |

| `getStudentInfo` | `(int student\_id)` | `ResultSet` | Retrieves detailed information for a specific student. |

| `studentCourses` | `(int student\_id)` | `ResultSet` | Retrieves the list of courses the student is currently enrolled in. |

| `getSupervisorInfo` | `(int student\_id)` | `ResultSet` | Retrieves information about the student's supervisor. |

| \*\*Enrollment Operations\*\* | | | |

| `enroll` | `(int student\_id, int course\_id)` | `boolean` | Enrolls a student in a specific course. |

| `updateEnrollment` | `(int student\_id, int course\_id, int grade)` | `boolean` | Updates the grade for a student's specific course enrollment. |

| `disenroll` | `(int student\_id, int course\_id)` | `boolean` | Removes (drops) a student from a course. |



---



\## 2. Lecturer Operations



Methods used for lecturers and supervision transactions.



| Method Name | Parameters | Return Type | Description |

| :--- | :--- | :--- | :--- |

| \*\*CRUD \& Filtering\*\* | | | |

| `addLecturer` | `(String name, String surname, String email, String dept, ...)` | `boolean` | Creates a new lecturer record in the database. |

| `deleteLecturer` | `(int lecturer\_id)` | `boolean` | Deletes the lecturer and related rows from other tables. |

| `updateLecturer` | `(int id, String name, String surname, String email, ...)` | `boolean` | Updates the information of an existing lecturer. |

| `lecturerFilter` | `(String name, String surname, int lecturer\_id, String dept, String sortBy)` | `ResultSet` | Retrieves a list of lecturers based on the specified conditions and sorting order. |

| \*\*Information Retrieval\*\* | | | |

| `getLecturerInfo` | `(int lecturer\_id)` | `ResultSet` | Retrieves detailed information for a specific lecturer. |

| `lecturerCourses` | `(int lecturer\_id)` | `ResultSet` | Retrieves the list of courses taught by the lecturer. |

| `getSupervisees` | `(int lecturer\_id)` | `ResultSet` | Retrieves the list of students supervised by the lecturer. |



---



\## 3. Course Operations



Methods used for course management, credits, and details.



| Method Name | Parameters | Return Type | Description |

| :--- | :--- | :--- | :--- |

| \*\*CRUD \& Filtering\*\* | | | |

| `addCourse` | `(int course\_id, String code, String name, int credits, String dept)` | `boolean` | Creates a new course record in the database. |

| `deleteCourse` | `(int course\_id)` | `boolean` | Deletes the course (only if no students are enrolled). |

| `updateCourse` | `(int id, String name, int credits, ...)` | `boolean` | Updates the information of an existing course. |

| `courseFilter` | `(String name, int course\_id, String dept, String sortBy)` | `ResultSet` | Retrieves a list of courses based on the specified conditions and sorting order. |

| \*\*Information Retrieval\*\* | | | |

| `getCourseInfo` | `(int course\_id)` | `ResultSet` | Retrieves detailed information (Credits, Code, etc.) for a specific course. |

| `courseStudents` | `(int course\_id)` | `ResultSet` | Retrieves the list of students enrolled in the specific course. |

| `getLecturerInfo` | `(int course\_id)` | `ResultSet` | Retrieves information about the lecturer teaching the specific course. |



---



\### 📌 Usage Notes

\* All methods must be called via the `DatabaseHelper` class.

\* Methods returning `boolean` will return `true` if the operation is successful, and `false` otherwise.

\* Methods returning `ResultSet` contain the data table retrieved from the database and are used to populate UI components like `JTable`.

