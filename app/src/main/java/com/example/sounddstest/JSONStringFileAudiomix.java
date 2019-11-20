package com.example.sounddstest;

public class JSONStringFileAudiomix {
    private String packName;
    private String titleText;
    private String seekBarColor;
    private String chipcoloron;
    private String chipcoloroff;
    private String textcolor;
    private boolean isPurchased;

    private String ambiencesoundsurl;
    private String subsound1url;
    private String subsound2url;
    private String subsound1Name;
    private String subsound2Name;
    private String timertextcolor;
    private String listViewImage;
    private String secondPageImage;
    private boolean show=false;
  //  public JSONStringFileAudiomix(){

   // }
  //  public JSONStringFileAudiomix(String packName,String titleText,String seekBarColor,String chipcoloron,String chipcoloroff,String textcolor,Boolean show,String ambiencesoundsurl,
    //                              String subsound1url, String subsound2url,String subsound1Name,String subsound2Name,String timertextcolor,String listViewImage,String secondPageImage) {

      public JSONStringFileAudiomix(){
        this.packName = packName;
        this.titleText=titleText;
        this.seekBarColor=seekBarColor;
        this.chipcoloron=chipcoloron;
        this.chipcoloroff=chipcoloroff;
        this.textcolor=textcolor;
        this.ambiencesoundsurl=ambiencesoundsurl;
        this.subsound1url=subsound1url;
        this.subsound2url=subsound2url;
        this.subsound1Name=subsound1Name;
        this.subsound2Name=subsound2Name;
        this.timertextcolor=timertextcolor;
        this.listViewImage=listViewImage;
        this.secondPageImage=secondPageImage;
          this.show=false;
        //,String bgListImage,String mainAudio,String seekBarColor
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getChipcoloron() {
        return chipcoloron;
    }

    public void setChipcoloron(String chipcoloron) {
        this.chipcoloron = chipcoloron;
    }

    public String getChipcoloroff() {
        return chipcoloroff;
    }

    public void setChipcoloroff(String chipcoloroff) {
        this.chipcoloroff = chipcoloroff;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }





    public String getSeekBarColor() {
        return seekBarColor;
    }

    public void setSeekBarColor(String seekBarColor) {
        this.seekBarColor = seekBarColor;
    }


    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }



    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getAmbiencesoundsurl() {
        return ambiencesoundsurl;
    }

    public void setAmbiencesoundsurl(String ambiencesoundsurl) {
        this.ambiencesoundsurl = ambiencesoundsurl;
    }

    public String getSubsound1url() {
        return subsound1url;
    }

    public void setSubsound1url(String subsound1url) {
        this.subsound1url = subsound1url;
    }

    public String getSubsound2url() {
        return subsound2url;
    }

    public void setSubsound2url(String subsound2url) {
        this.subsound2url = subsound2url;
    }

    public String getSubsound1Name() {
        return subsound1Name;
    }

    public void setSubsound1Name(String subsound1Name) {
        this.subsound1Name = subsound1Name;
    }

    public String getSubsound2Name() {
        return subsound2Name;
    }

    public void setSubsound2Name(String subsound2Name) {
        this.subsound2Name = subsound2Name;
    }

    public String getTimertextcolor() {
        return timertextcolor;
    }

    public void setTimertextcolor(String timertextcolor) {
        this.timertextcolor = timertextcolor;
    }

    public String getListViewImage() {
        return listViewImage;
    }

    public void setListViewImage(String listViewImage) {
        this.listViewImage = listViewImage;
    }

    public String getSecondPageImage() {
        return secondPageImage;
    }

    public void setSecondPageImage(String secondPageImage) {
        this.secondPageImage = secondPageImage;
    }
}

