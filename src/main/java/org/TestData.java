package org;

/*
Тестовые данные для изначального заполнения пустой базы данных
 */
public class TestData {

    public static void main(String[] args) {
        Repository repository = new Repository();

        repository.addUser("John Snow", "realstark@google.com", "jf11EEjfjf");
        repository.addUser("Darth Vader", "deathstar@google.com", "jf11EEjfjf");
        repository.addUser("Mario", "mario@google.com", "jfjfj11122R2f");
        repository.addUser("Luigi", "luigi@google.com", "jfjf22jRf");
        repository.addUser("Jack Sparrow", "captainJack@google.com", "jfRjfj111222f");
        repository.addUser("Dirty Harry", "magnum44@google.com", "jfj11fRjf");
        repository.addUser("Max Payne", "payne@google.com", "jfjfj11Hf");

//        repository.getUser(3);
//        repository.changeUser(4,"Prince Igor", "rusforever@google.com", "jfjfjf");
//        repository.getAllUsers();
//        repository.deleteUser(3);
    }
}
