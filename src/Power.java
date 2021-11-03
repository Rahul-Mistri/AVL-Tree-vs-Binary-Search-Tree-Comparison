public class Power implements Comparable<Power> {

    //private fields
    private String date;
    private String power;
    private String voltage;
    private boolean dataIn = false;

    //constructors
    public Power(){
        date = "";
        power = "";
        voltage = "";
    }
    public Power(String s){
        String arr[] = s.split(",");
        date = arr[0];
        power = arr[1];
        voltage= arr[3];
    }
    public Power(String date, String power, String voltage){
        this.date= date;
        this.power = power;
        this.voltage = voltage;
        dataIn = true;
    }

    public Power(Power toCopy)
    {
        this.date = toCopy.date;
        this.voltage = toCopy.voltage;
        this.power= toCopy.power;
    }
    //toString

    /**
     * toString method
     * @return  String in the format dd/mm/yyyy  powerRecording  voltageRecording
     */
    @Override
    public String toString() {
        return (date +" "+ power +" "+ voltage);
    }

    //getDate
    public String getDate() {
        return date;
    }



    //implementing the comparable interface to order the power objects

    /**
     * This method is used to implement the comparable interface by comparing the date recorded of the power recording.
     * This method is called from a power object, and compares to another power object in the parameter.
     *
     * @param o The object 'o' is of type Power.
     * @return This method returns 0 if the two were recorded on the same date, -1 if the calling Power object was recorded prior to the parameter object and 1 if the calling Power object was recorded after the paramater object.
     */
    public int compareTo(Power o){
        //first check for equality
        if (this.date.equalsIgnoreCase(o.date))
        {
            return 0; //test happens on same day
        }
        String original[] = this.date.split("/");
        String param[] = o.date.split("/");


        //  16/12/2006/20:00:00
        //  17/12/2006/00:02:00
        //splitting up the date as this is how it will be organised by, will check for lowest date, then if all ==, then lowest time
        if ((Integer.parseInt(original[2]))<(Integer.parseInt(param[2]))){

            return -1; //-1 implies calling object is less than parameter object according to year
        }
        else if ((Integer.parseInt(original[2]))>(Integer.parseInt(param[2]))){

            return 1;
        }
        else if ((Integer.parseInt(original[1]))<(Integer.parseInt(param[1]))){

            return -1; //-1 implies calling object is less than parameter object according to month
        }
        else if ((Integer.parseInt(original[1]))>(Integer.parseInt(param[1]))){

            return 1;
        }
        else if ((Integer.parseInt(original[0]))<(Integer.parseInt(param[0]))){

            return -1; //-1 implies calling object is less than parameter object according to day
        }
        else if ((Integer.parseInt(original[0]))>(Integer.parseInt(param[0]))){

            return 1;
        }
        else { //here the readings happen on same day and month and year, must check time
            //time split has format hour, minute, second
            String oTimeSplit[] = original[3].split(":");
            String pTimeSplit[] = param[3].split(":");
            if ((Integer.parseInt(oTimeSplit[0]))<(Integer.parseInt(pTimeSplit[0])))
            {
                return -1; //-1 implies calling object is less than parameter object according to hour of occurrence
            }
            else if ((Integer.parseInt(oTimeSplit[0]))>(Integer.parseInt(pTimeSplit[0])))
            {
                return 1;
            }
            else if ((Integer.parseInt(oTimeSplit[1]))<(Integer.parseInt(pTimeSplit[1])))
            {
                return -1; //-1 implies calling object is less than parameter object according to minute of occurrence
            }
            else if ((Integer.parseInt(oTimeSplit[1]))>(Integer.parseInt(pTimeSplit[1])))
            {
                return 1;
            }
            else if ((Integer.parseInt(oTimeSplit[2]))<(Integer.parseInt(pTimeSplit[2]))) {

                return -1; //-1 implies calling object is less than parameter object according to second of occurrence
            }
        }
        return 1; //clearly since they aren't equal and calling object was never less than parameter object, the
        //parameter object is less than the calling object and 1 is returned


    }

    /**
     * standard copy method to call copy constructor
     */
    public Power copy()
    {
        return new Power(this);
    }


}
