
package Data.Things;

public class Doctor {
        private long id;
        private String name;
        private String surname;
        private String patronymic;
        private String specialization;
        private int statistic;

        public Doctor() {
        }

        public Doctor(long id, String name, String surname, String patronymic, String specialization) {
                this.id = id;
                this.name = name;
                this.surname = surname;
                this.patronymic = patronymic;
                this.specialization = specialization;
        }

        public void setId(long id) {
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

        public void setSpecialization(String specialization) {
                this.specialization = specialization;
        }

        public void setStatistic(int statistic) {
                this.statistic = statistic;
        }
        
        

        public long getId() {
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

        public String getSpecialization() {
                return specialization;
        }

        public int getStatistic() {
                return statistic;
        }
        
        public String getInfoStatistic() {
        return name + " " + surname + " " + patronymic;
        }
        
}
