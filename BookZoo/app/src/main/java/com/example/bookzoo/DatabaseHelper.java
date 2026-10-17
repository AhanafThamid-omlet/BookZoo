package com.example.bookzoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookZoo.db";
    private static final int DATABASE_VERSION = 8;


    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_USERS = "users";


    private static final String COLUMN_ID = "id";


    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY = "category";


    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_CATEGORY + " TEXT"
                + ")";


        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";

        db.execSQL(CREATE_BOOKS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);


        insertSampleBooks(db);
    }


    public boolean updateUserProfile(String oldEmail, String newUsername, String newEmail, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", newUsername);
        values.put("email", newEmail);
        values.put("password", newPassword);


        int result = db.update("users", values, "email = ?", new String[]{oldEmail});
        return result > 0;
    }


    public Cursor getUserDetails(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("users", new String[]{"username", "email", "password"},
                "email = ?", new String[]{email}, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }


    public boolean addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        try {
            db.insertOrThrow(TABLE_USERS, null, values);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=?", new String[]{email},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKS, null, null, null, null, null, null);
    }

    public Cursor getBooksByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKS, null,
                COLUMN_CATEGORY + "=?", new String[]{category},
                null, null, null);
    }

    private void insertSampleBooks(SQLiteDatabase db) {
        String[][] bookData = {
                {"Python Programming", "19.99", "img", "Learn Python programming from scratch with this comprehensive guide.", "Programming"},
                {"Java Basics", "14.50", "img_1", "Master the basics of Java programming with this beginner-friendly book.", "Programming"},
                {"Android Development", "25.00", "img_2", "Build Android apps with this step-by-step development guide.", "Programming"},
                {"Sherlock Holmes", "18.99", "img_5", "A classic mystery novel by Arthur Conan Doyle.", "Mystery"},
                {"World War II History", "20.00", "img_2", "A detailed account of World War II events.", "History"},
                {"The Diary of Anne Frank", "15.75", "img_1", "The autobiography of Anne Frank during World War II.", "Autobiography"},
                {"Political Ideologies", "22.50", "img_3", "An overview of major political ideologies.", "Politics"},
                {"Steve Jobs Biography", "24.99", "img_4", "The biography of Steve Jobs, the co-founder of Apple.", "Biographies"},
                {"Web Development", "22.99", "img_5", "Learn web development with HTML, CSS, and JavaScript.", "Programming"},
                {"Machine Learning Basics", "29.50", "img_2", "Understand the basics of machine learning and its applications.", "Programming"},
                {"Data Structures & Algorithms", "24.75", "img", "A comprehensive guide to data structures and algorithms.", "Programming"},
                {"JavaScript Fundamentals", "17.99", "img_1", "Learn the fundamentals of JavaScript programming.", "Programming"},
                {"Full-Stack Web Dev", "23.99", "img_4", "Learn to build complete web applications using both frontend and backend technologies like HTML, CSS, JavaScript, Node.js, and databases.", "Mystery"},
                {"Django for Beginners", "18.00", "img_3", "Get started with Django, a powerful Python web framework, and build dynamic websites and applications with ease.", "History"},
                {"Data Science Toolkit", "25.50", "img_5", "Explore essential tools like NumPy, pandas, and Jupyter to begin your journey into data science and analytics.", "Mystery"},
                {"Cloud Computing Basics", "20.75", "img_1", "Understand the fundamentals of cloud platforms such as AWS, Azure, and Google Cloud, and learn how they are used in real-world applications.", "Biographies"},
                {"Ethical Hacking Guide", "22.10", "img_3", "Learn how to test and secure systems by understanding ethical hacking tools and methodologies used by professionals.", "Politics"},
                {"UI/UX Design Basics", "16.99", "img_2", "Discover the key principles of user interface and user experience design to create intuitive and visually appealing products.", "Politics"},
                {"C# Programming Guide", "17.25", "img_1", "Master the basics of C# programming language and use it to build desktop applications and Unity-based games.", "Politics"},
                {"Linux Command Line", "13.80", "img_4", "Learn the most important Linux commands and concepts to navigate, manage, and automate tasks in a Linux environment.", "History"},
                {"Agile Project Management", "14.60", "img_4", "Understand the principles of Agile development, including Scrum and Kanban, to manage software projects effectively.", "Programming"},
                {"Intro to Blockchain", "21.40", "img_5", "Gain a foundational understanding of blockchain technology, its use in cryptocurrencies, and its potential in other industries.", "Biographies"}


        };

        for (String[] book : bookData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, book[0]);
            values.put(COLUMN_PRICE, Double.parseDouble(book[1]));
            values.put(COLUMN_IMAGE, book[2]);
            values.put(COLUMN_DESCRIPTION, book[3]);
            values.put(COLUMN_CATEGORY, book[4]);
            db.insert(TABLE_BOOKS, null, values);
        }
    }
}