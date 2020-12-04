
package Data.Things;

public class Patient {
        private long id;
        private String name;
        private String surname;
        private String patronymic;
        private String telephone;

        public Patient(long id, String name, String surname, String patronymic, String telephone) {
                this.id = id;
                this.name = name;
                this.surname = surname;
                this.patronymic = patronymic;
                this.telephone = telephone;
        }

        public Patient() {
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

        public String getTelephone() {
                return telephone;
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

        public void setTelephone(String telephone) {
                this.telephone = telephone;
        }
        
}

