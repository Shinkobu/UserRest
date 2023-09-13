package org;

public class TestData {
    public static void main(String[] args) {
        Repository repository = new Repository();

        repository.addUser("John Snow", "realstark@google.com", "jfjfjf");
        repository.addUser("Darth Vader", "deathstar@google.com", "jfjfjf");
        repository.addUser("Mario", "mario@google.com", "jfjfj111222f");
        repository.addUser("Luigi", "luigi@google.com", "jfjfjf");
        repository.addUser("Jack Sparrow", "captainJack@google.com", "jfjfj111222f");
        repository.addUser("Dirty Harry", "magnum44@google.com", "jfjfjf");
        repository.addUser("Max Payne", "payne@google.com", "jfjfjf");

//        repository.getUser(3);
//        repository.changeUser(4,"Prince Igor", "rusforever@google.com", "jfjfjf");
//        repository.getAllUsers();
//        repository.deleteUser(3);
    }
}
