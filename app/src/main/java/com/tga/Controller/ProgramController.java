package com.tga.Controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tga.models.DiscountModel;
import com.tga.models.ProgramModel;

import java.util.ArrayList;

public class ProgramController implements DB_Interface{

    private ProgramModel programModel;
    private Discount discount;
    private DatabaseReference dbRef;

    public ProgramController(String id, String title, ArrayList<String> placesID, String description,
                        String startDate, String endDate, String hotelName, String discountID,
                             int rate, int hitRate, ArrayList<String> reviews, ArrayList<String> regisID){
        this.programModel = new ProgramModel();
        programModel.id = id;
        programModel.title = title;
        programModel.placesID = placesID;
        programModel.description = description;
        programModel.startDate = startDate;
        programModel.endDate = endDate;
        programModel.hotelName = hotelName;
        programModel.discountID = discountID;
        programModel.rate = rate;
        programModel.hitRate = hitRate;
        programModel.reviews = reviews;
        programModel.registeredTouristsID = regisID;
    }

    public String getId() {
        return programModel.id;
    }

    public String getTitle() {
        return programModel.title;
    }

    public void setTitle(String title) {
        programModel.title = title;
    }

    public ArrayList<String> getPlacesID() {
        return programModel.placesID;
    }

    public void addPlaceID(String placeID) {
        programModel.placesID.add(placeID);
    }

    public String getDescription() {
        return programModel.description;
    }

    public void setDescription(String description) {
        programModel.description = description;
    }

    public String getStartDate() {
        return programModel.startDate;
    }

    public void setStartDate(String startDate) {
        programModel.startDate = startDate;
    }

    public String getEndDate() {
        return programModel.endDate;
    }

    public void setEndDate(String endDate) {
        programModel.endDate = endDate;
    }

    public String getHotelName() {
        return programModel.hotelName;
    }

    public void setHotelName(String hotelName) {
        programModel.hotelName = hotelName;
    }

    public double getRate(){
        if (programModel.hitRate == 0)
            return 0;
        return (double) programModel.rate / programModel.hitRate;
    }

    public void addReview(String review){
        programModel.reviews.add(review);
    }

    public ArrayList<String> getReviews() {
        return programModel.reviews;
    }

    public ArrayList<String> getRegisteredList(){
        return programModel.registeredTouristsID;
    }

    public void registeTourist(String touristID){
        programModel.registeredTouristsID.add(touristID);
    }

    public void saveToDB(){
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.child(this.programModel.id).setValue(this.programModel);
    }

    @Override
    public void updateToDB() {
        saveToDB();
    }

    @Override
    public ArrayList<Object> listAll() {
        return null;
    }

    public void editProgram(String title, String description, String startDate,
                            String endDate, String hotelName) {
        this.setTitle(title);
        this.setDescription(description);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setHotelName(hotelName);
    }

    public void delFromDB() {
        //data base delete prog, it's reviews and discount
        if (this.programModel.reviews.size() > 0) {
            dbRef = FirebaseDatabase.getInstance().getReference("Reviews");
            for (String s : this.programModel.reviews ) {
                dbRef.child(s).setValue(null);
            }
        }
        if (this.discount != null){
            delDiscount();
        }
        dbRef = FirebaseDatabase.getInstance().getReference("Programs");
        dbRef.child(this.programModel.id).setValue(null);
    }

    public void rate(int n) {
        if (n > 5) { // for any error reason made rate > 5
            n %= 5;
            if (n == 0) // n = 10, 15, 20 or more
                n = 5;
        }
        this.programModel.hitRate++;
        this.programModel.rate += n;
    }

    public void setDiscount(String id, String endDate, double discountPercentage){
        discount = new Discount(id, endDate, discountPercentage);
        this.programModel.discountID = id;
    }

    public String getDiscountID() {
        return programModel.discountID;
    }

    public void delDiscount() {
        discount.delFromDB();
        discount = null;
        programModel.discountID = "";
    }

    public void editDiscount(String endDate, double discountPercentage) {
        discount.edit(endDate, discountPercentage);
    }

    private class Discount implements DB_Interface{

        public DiscountModel discountModel;

        public Discount(String id, String endDate, double discountPercentage){
            discountModel.id = id;
            discountModel.endDate = endDate;
            discountModel.discountPercentage = discountPercentage;
        }

        public String getId() {
            return discountModel.id;
        }

        public void setId(String id) {
            discountModel.id = id;
        }

        public String getEndDate() {
            return discountModel.endDate;
        }

        public void setEndDate(String endDate) {
            discountModel.endDate = endDate;
        }

        public double getDiscountPercentage() {
            return discountModel.discountPercentage;
        }

        public void setDiscountPercentage(double discountPercentage) {
            discountModel.discountPercentage = discountPercentage;
        }

        public void edit(String endDate, double discountPercentage) {
            this.discountModel.discountPercentage = discountPercentage;
            this.discountModel.endDate = endDate;
            updateToDB();
        }

        public void saveToDB(){
            dbRef = FirebaseDatabase.getInstance().getReference("Discounts");
            dbRef.child(this.discountModel.id).setValue(this.discountModel);
        }

        @Override
        public void updateToDB() {
            saveToDB();
        }

        @Override
        public ArrayList<Object> listAll() {
            return null;
        }

        public void delFromDB() {
            dbRef = FirebaseDatabase.getInstance().getReference("Discounts");
            dbRef.child(this.discountModel.id).setValue(null);
        }
    }
}
