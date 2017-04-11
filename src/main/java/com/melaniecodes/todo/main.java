package com.melaniecodes.todo;

import static spark.Spark.get;

public class main
{

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
        get("/", (req, res) -> "Welcome To My Site");
    }


}
