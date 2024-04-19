package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name and Version
    public static final String TABLE_CARS = "cars";
    public static final String TABLE_CARS2 = "cars2";
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_CAR_NAME = "car_name";
    public static final String COLUMN_CAR_NUMBER = "car_number";

    // Table name and columns for Rentals
    public static final String TABLE_RENTALS = "rentals";
    public static final String COLUMN_RENTAL_ID = "rental_id";
    public static final String COLUMN_USER_EMAIL_RENTAL = "user_email_rental";
    public static final String COLUMN_CAR_ID_RENTAL = "car_id_rental";
    public static final String COLUMN_RENTAL_START_DATE = "rental_start_date";
    public static final String COLUMN_RENTAL_END_DATE = "rental_end_date";
    public static final String COLUMN_PAYMENT_AMOUNT = "payment_amount";

    // Table name and columns for Favorites
    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_USER_EMAIL_FAVORITE = "user_email_favorite";
    public static final String COLUMN_CAR_ID_FAVORITE = "car_id_favorite";

    private static final String DATABASE_NAME = "YourDatabaseName";

    // Table name and columns
    public static final String TABLE_USER = "user";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    public static final String COLUMN_CAR_PRICE = "price";
    public static final String COLUMN_CAR_MODEL = "model";
    public static final String COLUMN_FUEL_TYPE = "fuel_type";
    public static final String COLUMN_TRANSMISSION = "transmission";
    public static final String COLUMN_MILEAGE = "mileage";

    // Other table names and columns...

    private static final String CREATE_TABLE_CARS = "CREATE TABLE " +
            TABLE_CARS + " (" +
            COLUMN_CAR_ID + " INTEGER PRIMARY KEY," +
            COLUMN_CAR_NAME + " TEXT," +
            COLUMN_CAR_NUMBER + " INTEGER," +
            COLUMN_CAR_PRICE + " REAL," + // For storing decimal values
            COLUMN_CAR_MODEL + " TEXT," +
            COLUMN_FUEL_TYPE + " TEXT," +
            COLUMN_TRANSMISSION + " TEXT," +
            COLUMN_MILEAGE + " INTEGER" + // Assuming mileage is stored as an integer value
            ")";


    private static final int DATABASE_VERSION = 4; // Increment the version number
    public static final String TABLE_SPECIAL_OFFERS = "special_offers";
    public static final String COLUMN_SPECIAL_OFFER_ID = "special_offer_id";
    public static final String COLUMN_CAR_ID_SPECIAL_OFFER = "car_id_special_offer";
    public static final String COLUMN_PRICE_BEFORE = "price_before";
    public static final String COLUMN_PRICE_AFTER = "price_after";

    // ... (your existing code)

    private static final String CREATE_TABLE_SPECIAL_OFFERS = "CREATE TABLE " +
            TABLE_SPECIAL_OFFERS + " (" +
            COLUMN_SPECIAL_OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CAR_ID_SPECIAL_OFFER + " INTEGER," +
            COLUMN_PRICE_BEFORE + " REAL," +
            COLUMN_PRICE_AFTER + " REAL" +
            ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CARS);
        db.execSQL(CREATE_TABLE_RENTALS);
        db.execSQL(CREATE_TABLE_FAVORITES);
        db.execSQL(CREATE_TABLE_SPECIAL_OFFERS);
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(ALTER_TABLE_RESERVATIONS_ADD_CAR_TYPE);
        insertInitialAdmin(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle the database schema upgrade here
        if (oldVersion < 2) {
            // Upgrade logic for version 1 to version 2
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
            db.execSQL(CREATE_TABLE_CARS2);

        }
        if (oldVersion < 3) {  // Assuming 3 is the new version
            db.execSQL(CREATE_TABLE_ADMIN);
        //    db.execSQL(CREATE_TABLE_SPECIAL_OFFERS);
            insertInitialAdmin(db);
        }
        if(oldVersion< 4){
            db.execSQL(CREATE_TABLE_SPECIAL_OFFERS);
        }

        // Add more upgrade logic for future versions as needed
    }

    private static final String CREATE_TABLE_CARS2 = "CREATE TABLE " +
            TABLE_CARS2 + " (" +
            COLUMN_CAR_ID + " INTEGER PRIMARY KEY," +
            COLUMN_CAR_NAME + " TEXT," +
            COLUMN_CAR_NUMBER + " INTEGER," +
            COLUMN_CAR_PRICE + " REAL," + // For storing decimal values
            COLUMN_CAR_MODEL + " TEXT," +
            COLUMN_FUEL_TYPE + " TEXT," +
            COLUMN_TRANSMISSION + " TEXT," +
            COLUMN_MILEAGE + " INTEGER" + // Assuming mileage is stored as an integer value
            ")";
    private static final String CREATE_TABLE_USER = "CREATE TABLE " +
            TABLE_USER + " (" +
            COLUMN_EMAIL + " TEXT PRIMARY KEY," +
            COLUMN_FIRST_NAME + " TEXT," +
            COLUMN_LAST_NAME + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_COUNTRY + " TEXT," +
            COLUMN_CITY + " TEXT," +
            COLUMN_PHONE_NUMBER + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


public static final String COLUMN_CAR_TYPE = "car_type";

    private static final String CREATE_TABLE_RENTALS = "CREATE TABLE " +
            TABLE_RENTALS + " (" +
            COLUMN_RENTAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_EMAIL_RENTAL + " TEXT," +
            COLUMN_CAR_ID_RENTAL + " TEXT," +
            COLUMN_RENTAL_START_DATE + " TEXT," +
            COLUMN_RENTAL_END_DATE + " TEXT," +
            COLUMN_PAYMENT_AMOUNT + " REAL" +
            ")";

    private static final String ALTER_TABLE_RESERVATIONS_ADD_CAR_TYPE =
            "ALTER TABLE " + TABLE_RENTALS + " ADD COLUMN " + COLUMN_CAR_TYPE + " TEXT";



    private static final String CREATE_TABLE_FAVORITES = "CREATE TABLE " +
            TABLE_FAVORITES + " (" +
            COLUMN_USER_EMAIL_FAVORITE + " TEXT," +
            COLUMN_CAR_ID_FAVORITE + " INTEGER," +
            "PRIMARY KEY (" + COLUMN_USER_EMAIL_FAVORITE + ", " + COLUMN_CAR_ID_FAVORITE + ")" +
            ")";



    public Car getCarById(Context context,int carId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Car car = null;

        String query = "SELECT * FROM " + TABLE_CARS2 + " WHERE " + COLUMN_CAR_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(carId)});

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(COLUMN_CAR_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_CAR_NAME);
                int numberIndex = cursor.getColumnIndex(COLUMN_CAR_NUMBER);
                int priceIndex = cursor.getColumnIndex(COLUMN_CAR_PRICE);
                int modelIndex = cursor.getColumnIndex(COLUMN_CAR_MODEL);
                int fuelTypeIndex = cursor.getColumnIndex(COLUMN_FUEL_TYPE);
                int transmissionIndex = cursor.getColumnIndex(COLUMN_TRANSMISSION);
                int mileageIndex = cursor.getColumnIndex(COLUMN_MILEAGE);

                if (idIndex != -1 && nameIndex != -1) {
                    // Assuming you have a Car class with a constructor that takes these parameters
                    car = new Car(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getInt(numberIndex),
                            cursor.getDouble(priceIndex),
                            cursor.getString(modelIndex),
                            cursor.getString(fuelTypeIndex),
                            cursor.getString(transmissionIndex),
                            cursor.getInt(mileageIndex)
                    );
                }
            } else {
                // Handle the case where the car with the specified ID is not found
           //     Toast.makeText(context, "Car with ID " + carId + " not found", Toast.LENGTH_SHORT).show();
           }
        } catch (Exception e) {
            // Handle any exceptions that may occur during database operations
         //   Toast.makeText(context, "Error retrieving car by ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return car;
    }
    public String getModelCarById(Context context,int carId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Model="";

        String query = "SELECT * FROM " + TABLE_CARS2 + " WHERE " + COLUMN_CAR_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(carId)});

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(COLUMN_CAR_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_CAR_NAME);
                int numberIndex = cursor.getColumnIndex(COLUMN_CAR_NUMBER);
                int priceIndex = cursor.getColumnIndex(COLUMN_CAR_PRICE);
                int modelIndex = cursor.getColumnIndex(COLUMN_CAR_MODEL);
                int fuelTypeIndex = cursor.getColumnIndex(COLUMN_FUEL_TYPE);
                int transmissionIndex = cursor.getColumnIndex(COLUMN_TRANSMISSION);
                int mileageIndex = cursor.getColumnIndex(COLUMN_MILEAGE);

                if (idIndex != -1 && nameIndex != -1) {
                    // Assuming you have a Car class with a constructor that takes these parameters
                    Model=cursor.getString(modelIndex);

                }
            } else {
                // Handle the case where the car with the specified ID is not found
                //     Toast.makeText(context, "Car with ID " + carId + " not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur during database operations
            //   Toast.makeText(context, "Error retrieving car by ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return Model;
    }




    // Method to get user data based on email
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] columns = {
                COLUMN_EMAIL,
                COLUMN_FIRST_NAME,
                COLUMN_LAST_NAME,
                COLUMN_GENDER,
                COLUMN_PASSWORD,
                COLUMN_COUNTRY,
                COLUMN_CITY,
                COLUMN_PHONE_NUMBER
        };

        String selection = COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            user=User.getInstance();
            int columnIndexEmail = cursor.getColumnIndex(COLUMN_EMAIL);
            int columnIndexFirstName = cursor.getColumnIndex(COLUMN_FIRST_NAME);
            int columnIndexLastName = cursor.getColumnIndex(COLUMN_LAST_NAME);
            int columnIndexGender = cursor.getColumnIndex(COLUMN_GENDER);
            int columnIndexPassword = cursor.getColumnIndex(COLUMN_PASSWORD);
            int columnIndexCountry = cursor.getColumnIndex(COLUMN_COUNTRY);
            int columnIndexCity = cursor.getColumnIndex(COLUMN_CITY);
            int columnIndexPhoneNumber = cursor.getColumnIndex(COLUMN_PHONE_NUMBER);

            if (columnIndexEmail != -1) {
                user.setEmail(cursor.getString(columnIndexEmail));
            }

            if (columnIndexFirstName != -1) {
                user.setFirstName(cursor.getString(columnIndexFirstName));
            }

            if (columnIndexLastName != -1) {
                user.setLastName(cursor.getString(columnIndexLastName));
            }

            if (columnIndexGender != -1) {
                user.setGender(cursor.getString(columnIndexGender));
            }

            if (columnIndexPassword != -1) {
                user.setPassword(cursor.getString(columnIndexPassword));
            }

            if (columnIndexCountry != -1) {
                user.setCountry(cursor.getString(columnIndexCountry));
            }

            if (columnIndexCity != -1) {
                user.setCity(cursor.getString(columnIndexCity));
            }

            if (columnIndexPhoneNumber != -1) {
                user.setPhoneNumber(cursor.getString(columnIndexPhoneNumber));
            }

            cursor.close();
        }

        return user;
    }
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_COUNTRY, user.getCountry());
        values.put(COLUMN_CITY, user.getCity());
        values.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        // ... add other columns as needed

        // Inserting Row
        long result = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
        return result;
    }


    public int getCarCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            String countQuery = "SELECT * FROM " + TABLE_CARS;
            cursor = db.rawQuery(countQuery, null);
            count = cursor.getCount();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return count;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        values.put(COLUMN_CITY, user.getCity());
        values.put(COLUMN_COUNTRY, user.getCountry());
        // ... add other columns as needed

        // Update the user record
        db.update(
                TABLE_USER,
                values,
                COLUMN_EMAIL + " = ?",
                new String[]{user.getEmail()}
        );

        db.close(); // Closing database connection
    }
    private static final String ALTER_TABLE_ADD_COLUMN =
            "ALTER TABLE %s ADD COLUMN %s %s";
    public void addNewColumn(String tableName, String columnName, String columnType) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = String.format(Locale.US, ALTER_TABLE_ADD_COLUMN, tableName, columnName, columnType);

        try {
            db.execSQL(sql);
            Log.d("DatabaseHelper", "Added new column: " + columnName + " to table: " + tableName);
        } catch (SQLiteException e) {
            Log.e("DatabaseHelper", "Error adding column: " + columnName + " to table: " + tableName, e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public long addCar(Context context, String carId, String carType, int des, int cost, String transmission, int mile, String fuelType, String model) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Check if the car with the given ID already exists
            if (!isCarExists(carId, db)) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CAR_ID, carId);
                //values.put(COLUMN_CAR_TYPE, carType);
                values.put(COLUMN_CAR_PRICE, cost);
                values.put(COLUMN_MILEAGE, mile);
                values.put(COLUMN_FUEL_TYPE, fuelType);
                values.put(COLUMN_CAR_MODEL, model);

                // Insert the new car into the database
                long newRowId = db.insertOrThrow(TABLE_CARS2, null, values);
                db.close();

                if (newRowId != -1) {
                    // Insertion successful
//                    Toast.makeText(context, "Car added successfully:\n" +
//                            "Car ID: " + carId + "\n" +
//                            "Car Type: " + carType + "\n" +
//                            "Cost: " + cost + "\n" +
//                            "Transmission: " + transmission + "\n" +
//                            "Mile: " + mile + "\n" +
//                            "Fuel Type: " + fuelType + "\n" +
//                            "Model: " + model, Toast.LENGTH_SHORT).show();
                } else {
                    // Insertion failed
                    // Toast.makeText(context, "Failed to add car", Toast.LENGTH_SHORT).show();
                }

                return newRowId;
            } else {
                // Car with the given ID already exists, handle accordingly
               // Toast.makeText(context, "Car with ID " + carId + " already exists", Toast.LENGTH_SHORT).show();
                return -1;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
           // Toast.makeText(context, "Error adding car: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            // Log the specific error message
            Log.e("SQLiteError", e.getMessage());

            return -1; // Indicate failure
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public long addRental(String userEmail, String carId, String startDate, String endDate, double paymentAmount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL_RENTAL, userEmail);
        values.put(COLUMN_CAR_ID_RENTAL, carId);
        values.put(COLUMN_RENTAL_START_DATE, startDate);
        values.put(COLUMN_RENTAL_END_DATE, endDate);
        values.put(COLUMN_PAYMENT_AMOUNT, paymentAmount);

        // Inserting Row
        long result = db.insert(TABLE_RENTALS, null, values);
        db.close(); // Closing database connection
        return result;
    }
    private boolean isCarExists(String carId, SQLiteDatabase db) {
        String query = "SELECT COUNT(*) FROM " + TABLE_CARS2 +
                " WHERE " + COLUMN_CAR_ID + "=?";
        String[] selectionArgs = {carId};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count > 0;
    }
    // Method to retrieve a list of rentals for a specific userEmail
    public List<Rental> getRentalsForUser(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Rental> rentalList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RENTALS + " WHERE " + COLUMN_USER_EMAIL_RENTAL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userEmail});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int rentalIdIndex = cursor.getColumnIndex(COLUMN_RENTAL_ID);
                int userEmailIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL_RENTAL);
                int carIdIndex = cursor.getColumnIndex(COLUMN_CAR_ID_RENTAL);
                int startDateIndex = cursor.getColumnIndex(COLUMN_RENTAL_START_DATE);
                int endDateIndex = cursor.getColumnIndex(COLUMN_RENTAL_END_DATE);
                int paymentAmountIndex = cursor.getColumnIndex(COLUMN_PAYMENT_AMOUNT);

                if (rentalIdIndex != -1 && userEmailIndex != -1 && carIdIndex != -1 &&
                        startDateIndex != -1 && endDateIndex != -1 && paymentAmountIndex != -1) {
                    Rental rental = new Rental(
                            cursor.getInt(rentalIdIndex),
                            cursor.getString(userEmailIndex),
                            cursor.getString(carIdIndex),
                            cursor.getString(startDateIndex),
                            cursor.getString(endDateIndex),
                            cursor.getDouble(paymentAmountIndex)
                    );
                    rentalList.add(rental);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return rentalList;
    }

    public static final String TABLE_ADMIN = "AdminTable";
    public static final String COLUMN_ADMIN_ID = "id";
    public static final String COLUMN_ADMIN_EMAIL = "email";

    private static final String CREATE_TABLE_ADMIN = "CREATE TABLE " +
            TABLE_ADMIN + " (" +
            COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ADMIN_EMAIL + " TEXT NOT NULL" +
            ")";


    private void insertInitialAdmin(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_EMAIL, "tala@hotmail.com"); // Set the admin email

        db.insert(TABLE_ADMIN, null, values); // Insert the admin record
    }
    public boolean isAdminEmail(String email, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to check if the specific email exists
        String[] columns = {COLUMN_ADMIN_EMAIL};
        String selection = COLUMN_ADMIN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_ADMIN, columns, selection, selectionArgs, null, null, null);

        boolean exists = cursor.moveToFirst();
        cursor.close();

        // Query to get all admin emails
        Cursor allEmailsCursor = db.query(TABLE_ADMIN, new String[]{COLUMN_ADMIN_EMAIL}, null, null, null, null, null);
        StringBuilder allEmails = new StringBuilder();
        while (allEmailsCursor.moveToNext()) {
            allEmails.append(allEmailsCursor.getString(0)).append("\n");
        }
        allEmailsCursor.close();

        // Print all emails using Toast
     //   Toast.makeText(context, "thiissss frommmmm"+email+"________________________"+ allEmails.toString()+exists, Toast.LENGTH_LONG).show();

        db.close();
        return exists;
    }
    public List<Rental> getAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_RENTAL_ID,
                COLUMN_USER_EMAIL_RENTAL,
                COLUMN_CAR_ID_RENTAL,
                COLUMN_RENTAL_START_DATE,
                COLUMN_RENTAL_END_DATE,
                COLUMN_PAYMENT_AMOUNT
        };

        Cursor cursor = db.query(TABLE_RENTALS, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_RENTAL_ID);
            int userEmailIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL_RENTAL);
            int carIdIndex = cursor.getColumnIndex(COLUMN_CAR_ID_RENTAL);
            int startDateIndex = cursor.getColumnIndex(COLUMN_RENTAL_START_DATE);
            int endDateIndex = cursor.getColumnIndex(COLUMN_RENTAL_END_DATE);
            int paymentAmountIndex = cursor.getColumnIndex(COLUMN_PAYMENT_AMOUNT);

            do {
                if (idIndex != -1 && userEmailIndex != -1 && carIdIndex != -1 &&
                        startDateIndex != -1 && endDateIndex != -1 && paymentAmountIndex != -1) {
                    Rental rental = new Rental(
                            cursor.getInt(idIndex),
                            cursor.getString(userEmailIndex),
                            cursor.getString(carIdIndex),
                            cursor.getString(startDateIndex),
                            cursor.getString(endDateIndex),
                            cursor.getDouble(paymentAmountIndex)
                    );
                    rentals.add(rental);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return rentals;
    }

    public boolean addAdmin(String email) {
        if (!isAdminEmail(email)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ADMIN_EMAIL, email);

            long result = db.insert(TABLE_ADMIN, null, values);
            db.close();
            return result != -1;
        } else {
            return false; // Email already exists in admin table
        }
    }

    private boolean isAdminEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ADMIN_EMAIL};
        String selection = COLUMN_ADMIN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_ADMIN, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }

    public boolean deleteCustomer(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_USER, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();
        return deletedRows > 0;
    }

    public void applySpecialOfferForCars(Context context, int[] carIds, double discountPercentage) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int carId : carIds) {
            try {
                Cursor cursor = db.query(
                        TABLE_CARS2,
                        new String[]{COLUMN_CAR_ID, COLUMN_CAR_PRICE},
                        COLUMN_CAR_ID + "=?",
                        new String[]{String.valueOf(carId)},
                        null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                      int priceIndex = cursor.getColumnIndex(COLUMN_CAR_PRICE);
                    if (priceIndex != -1) {
                        double originalPrice = cursor.getDouble(priceIndex);
                        double discountedPrice = originalPrice * (1 - discountPercentage / 100);

                        ContentValues specialOfferValues = new ContentValues();
                        specialOfferValues.put(COLUMN_CAR_ID_SPECIAL_OFFER, carId);
                        specialOfferValues.put(COLUMN_PRICE_BEFORE, originalPrice);
                        specialOfferValues.put(COLUMN_PRICE_AFTER, discountedPrice);
//
//                        int updatedRows = db.update(
//                                TABLE_SPECIAL_OFFERS,
//                                specialOfferValues,
//                                COLUMN_CAR_ID_SPECIAL_OFFER + "=?",
//                                new String[]{String.valueOf(carId)});
//
                     //   Toast.makeText(context, "found for car ID: " + carId, Toast.LENGTH_SHORT).show();
                            db.insert(TABLE_SPECIAL_OFFERS, null, specialOfferValues);

                    } else {
                        Toast.makeText(context, "Price column not found for car ID: " + carId, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Car not found with ID: " + carId, Toast.LENGTH_SHORT).show();
                }

                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Error applying special offer for car ID: " + carId + ". " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        db.close();
    }

    public List<CarSpecialOffer> getAllSpecialOfferCars(Context context) {
        List<CarSpecialOffer> specialOfferCars = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_CARS2 + " INNER JOIN " + TABLE_SPECIAL_OFFERS +
                " ON " + TABLE_CARS2 + "." + COLUMN_CAR_ID + "=" + TABLE_SPECIAL_OFFERS + "." + COLUMN_CAR_ID_SPECIAL_OFFER;
        try {
            Cursor cursor = db.rawQuery(query, null);
            //Toast.makeText(context, "Error retrieving special offer cars: " + cursor, Toast.LENGTH_LONG).show();

            if (cursor.moveToFirst()) {
                do {
                    int carIdIndex = cursor.getColumnIndex(COLUMN_CAR_ID);
                    int carNameIndex = cursor.getColumnIndex(COLUMN_CAR_NAME);
                    int carPriceBeforeIndex = cursor.getColumnIndex(COLUMN_PRICE_BEFORE);
                    int carPriceAfterIndex = cursor.getColumnIndex(COLUMN_PRICE_AFTER);

                    if (carIdIndex != -1 && carNameIndex != -1 && carPriceBeforeIndex != -1 && carPriceAfterIndex != -1) {
                        CarSpecialOffer carSpecialOffer = new CarSpecialOffer(
                                cursor.getInt(carIdIndex),
                                cursor.getString(carNameIndex),
                                cursor.getDouble(carPriceBeforeIndex),
                                cursor.getDouble(carPriceAfterIndex)
                        );
                        specialOfferCars.add(carSpecialOffer);
                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
          //  Toast.makeText(context, "Error retrieving special offer cars: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }

        return specialOfferCars;
    }

    public boolean doesCarHaveSpecialOffer(SQLiteDatabase db, int carId) {
        boolean hasOffer = false;
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TABLE_SPECIAL_OFFERS,
                    new String[]{COLUMN_CAR_ID_SPECIAL_OFFER}, // Specify the column to check
                    COLUMN_CAR_ID_SPECIAL_OFFER + " = ?", // Condition
                    new String[]{String.valueOf(carId)}, // Condition values
                    null, null, null);

            // If the query returns one or more rows, then there is a special offer for the car
            hasOffer = (cursor != null && cursor.getCount() > 0);
        } catch (Exception e) {
            // Handle the error here
            Log.e("DatabaseHelper", "Error checking for special offer: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close(); // Make sure to close the cursor
            }
        }
        return hasOffer;
    }




}
