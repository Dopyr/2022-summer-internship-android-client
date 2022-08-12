package com.example.mysqlapp;

public enum Major {

    computer_science(1),
    veterinary(2),
    economy(3),
    statistics(4),
    biology(5);

    private int id;

    private Major(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public static Major valueOf(int id) {

        for (Major m : Major.values()) {

            if (m.id == id) {

                return m;
            }
        }

        return null;
    }
}
