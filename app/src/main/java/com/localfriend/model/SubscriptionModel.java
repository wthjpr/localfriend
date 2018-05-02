package com.localfriend.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubscriptionModel implements Serializable
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    public class Data
    {

        @SerializedName("packagedetails")
        @Expose
        private Packagedetails packagedetails;
        @SerializedName("status")
        @Expose
        private List<Status> status = null;
        @SerializedName("track")
        @Expose
        private List<Track> track = null;

        public Packagedetails getPackagedetails()
        {
            return packagedetails;
        }

        public void setPackagedetails(Packagedetails packagedetails)
        {
            this.packagedetails = packagedetails;
        }

        public List<Status> getStatus()
        {
            return status;
        }

        public void setStatus(List<Status> status)
        {
            this.status = status;
        }

        public List<Track> getTrack()
        {
            return track;
        }

        public void setTrack(List<Track> track)
        {
            this.track = track;
        }

        public class Packagedetails
        {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("startdate")
            @Expose
            private String startdate;
            @SerializedName("price")
            @Expose
            private String price;

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getStartdate()
            {
                return startdate;
            }

            public void setStartdate(String startdate)
            {
                this.startdate = startdate;
            }

            public String getPrice()
            {
                return price;
            }
            public void setPrice(String price) {
                this.price = price;
            }

        }

        public class Status {

            @SerializedName("meals")
            @Expose
            private String meals;
            @SerializedName("total")
            @Expose
            private String total;
            @SerializedName("remaining")
            @Expose
            private String remaining;
            @SerializedName("deleverd")
            @Expose
            private String deleverd;
            @SerializedName("canceled")
            @Expose
            private String canceled;

            public String getMeals() {
                return meals;
            }

            public void setMeals(String meals) {
                this.meals = meals;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getRemaining() {
                return remaining;
            }

            public void setRemaining(String remaining) {
                this.remaining = remaining;
            }

            public String getDeleverd() {
                return deleverd;
            }

            public void setDeleverd(String deleverd) {
                this.deleverd = deleverd;
            }

            public String getCanceled() {
                return canceled;
            }

            public void setCanceled(String canceled) {
                this.canceled = canceled;
            }

        }

        public class Track {

            @SerializedName("detailsname")
            @Expose
            private String detailsname;
            @SerializedName("breakfast")
            @Expose
            private String breakfast;
            @SerializedName("lunch")
            @Expose
            private String lunch;
            @SerializedName("dinner")
            @Expose
            private String dinner;

            public String getDetailsname() {
                return detailsname;
            }

            public void setDetailsname(String detailsname) {
                this.detailsname = detailsname;
            }

            public String getBreakfast() {
                return breakfast;
            }

            public void setBreakfast(String breakfast) {
                this.breakfast = breakfast;
            }

            public String getLunch() {
                return lunch;
            }

            public void setLunch(String lunch) {
                this.lunch = lunch;
            }

            public String getDinner() {
                return dinner;
            }

            public void setDinner(String dinner) {
                this.dinner = dinner;
            }

        }

    }
}
