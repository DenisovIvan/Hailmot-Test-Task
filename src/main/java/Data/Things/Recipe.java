package Data.Things;

import java.util.Date;

public class Recipe {

        private long id;
        private String discription;
        private RecipePatient patient;
        private RecipeDoctor doctor;
        private java.util.Date calendar;
        private int validaty;
        private RecipePriority priority;

        public Recipe(long id, String discription, RecipePatient patient, RecipeDoctor doctor, Date calendar, int validaty, RecipePriority priority) {
                this.id = id;
                this.discription = discription;
                this.patient = patient;
                this.doctor = doctor;
                this.calendar = calendar;
                this.validaty = validaty;
                this.priority = priority;
        }


        public Recipe() {
        }

        public void setId(long id) {
                this.id = id;
        }

        public void setDiscription(String discription) {
                this.discription = discription;
        }

        public void setCalendar(Date calendar) {
                this.calendar = calendar;
        }

        public void setValidaty(int validaty) {
                this.validaty = validaty;
        }

        public void setPatient(RecipePatient patient) {
                this.patient = patient;
        }

        public void setDoctor(RecipeDoctor doctor) {
                this.doctor = doctor;
        }

        public void setPriority(RecipePriority priority) {
                this.priority = priority;
        }

        public long getId() {
                return id;
        }

        public String getDiscription() {
                return discription;
        }

        public Date getCalendar() {
                return calendar;
        }

        public int getValidaty() {
                return validaty;
        }

        public RecipePatient getPatient() {
                return patient;
        }
        
                public String getPatInfo(){
                return this.patient.surname+" "+this.patient.name + " " + this.patient.patronymic;
}
        public String getDocInfo(){
                return this.doctor.surname + " " + this.doctor.name+" " +this.doctor.patronymic;
}

        public RecipeDoctor getDoctor() {
                return doctor;
        }

        public RecipePriority getPriority() {
                return priority;
        }

        public static class RecipePatient {

                private Long id;

                private String name;

                private String surname;

                private String patronymic;

                public void setId(Long id) {
                        this.id = id;
                }

                public void setName(String name) {
                        this.name = name;
                }

                public void setSurname(String surname) {
                        this.surname = surname;
                }

                public void setPatronymic(String patronymic) {
                        this.patronymic = patronymic;
                }

                public Long getId() {
                        return id;
                }

                public String getName() {
                        return name;
                }

                public String getSurname() {
                        return surname;
                }

                public String getPatronymic() {
                        return patronymic;
                }

                public RecipePatient(Long id) {
                        this.id = id;
                        this.name = "sample";
                        this.surname = "sample";
                        this.patronymic = "sample";
                }

                public RecipePatient(Long id, String name, String surname, String patronymic) {
                        this.id = id;
                        this.name = name;
                        this.surname = surname;
                        this.patronymic = patronymic;
                }

                public RecipePatient() {
                }

        }

        public static class RecipeDoctor {

                public RecipeDoctor(Long id) {
                        this.id = id;
                        this.name = "sample";
                        this.surname = "sample";
                        this.patronymic = "sample";
                }

                private Long id;

                private String name;

                private String surname;

                private String patronymic;

                public void setId(Long id) {
                        this.id = id;
                }

                public void setName(String name) {
                        this.name = name;
                }

                public void setSurname(String surname) {
                        this.surname = surname;
                }

                public void setPatronymic(String patronymic) {
                        this.patronymic = patronymic;
                }

                public Long getId() {
                        return id;
                }

                public String getName() {
                        return name;
                }

                public String getSurname() {
                        return surname;
                }

                public String getPatronymic() {
                        return patronymic;
                }

                public RecipeDoctor(Long id, String name, String surname, String patronymic) {
                        this.id = id;
                        this.name = name;
                        this.surname = surname;
                        this.patronymic = patronymic;
                }


                public RecipeDoctor() {
                }
        }

        public static class RecipePriority {

                public RecipePriority(Long id) {
                        this.id = id;
                        this.name = "sample";
                }
                

                private Long id;

                private String name;

                public void setId(Long id) {
                        this.id = id;
                }

                public void setName(String name) {
                        this.name = name;
                }

                public Long getId() {
                        return id;
                }

                public String getName() {
                        return name;
                }

                public RecipePriority(Long id, String name) {
                        this.id = id;
                        this.name = name;
                }

                public RecipePriority() {
                }
        }
}
