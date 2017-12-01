
/**
 * Class that processes the data
 */
import java.io.*;
import java.util.*;

public class RadonProcessor
{
    // instance variables
    private ArrayList<House> houseList;
    private static final String streetName = "Rue Marie Curie";

    /**
     * Null Constructor creates an empty houseList
     */
    public RadonProcessor()
    {
        houseList = new ArrayList<House>();
    }
    
    /**
     * Function that searches houseList for houseNumber
     * @param houseNumber
     * @return index if found otherwise -1
     */
    private int searchHouseNumber(int houseNumber)
    {
        int index = -1;
        for (int i = 0; i < houseList.size(); i++)
        {
            if (houseList.get(i).getHouseNo() == houseNumber)
            {
                index = i;
                break;
            }
        }
        return index;
    }
    
    /**
     * Print the house number and street name for every house in houseList
     */
    public void printBlockData()
    {
        System.out.println("Printing Block Data");
        for (House obj : houseList)
        {
            System.out.println(obj.getHouseNo() + "  " + obj.getStreet());
        }
    }
    
    /**
     * Prints the house details for every house in houseList
     */
    public void printHouseDetails()
    {
        System.out.println("Printing House Details");
        System.out.println("HouseNo\tStreetName\tBasementVol\tDailyRadonLevel\tYearlyRadonLevel");
        for (House obj : houseList)
        {
            System.out.println(obj + "\t" + obj.calcYearlyLevel());
        }
    }
    
    /**
     * @param houseNumber
     * Function removes the house specified by the user from the houselist
     */
    public void demolishHouse(int houseNumber)
    {
        int index = this.searchHouseNumber(houseNumber);
        if (index == -1)
        {
            System.out.println("House not found");    
        } else
        {
            System.out.println("Demolishing house " + houseNumber);
            houseList.remove(index);
        }
    }
    
    /**
     * @param  fileName
     * @return Boolean (true if loading is successful else false)
     */
    public Boolean loadData(String fileName)
    {
        File file = new File(fileName);
        FileReader in = null;
        BufferedReader br = null;
        String line1 = null;
        String line2 = null;
        String line3 = null;
        try
        {
            in = new FileReader(file);
            br = new BufferedReader(in);
            while ((line1 = br.readLine()) != null &&
                   (line2 = br.readLine()) != null &&
                   (line3 = br.readLine()) != null)
            {
                House newHouse = new House(Integer.parseInt(line1), 
                                            this.streetName,
                                            Double.parseDouble(line2),
                                            Double.parseDouble(line3));
                houseList.add(newHouse);
            }
        } catch (FileNotFoundException fnfe)
        {
            System.out.println("Data file not found " + fnfe);
            return false;
        }
        catch(IOException ioe)
        {
            System.out.println("I/O Exception: " + ioe);
            return false;
        }
        finally {
            try
            {
                if (br != null)
                {
                    br.close();
                }
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException iex)
            {
                System.out.println("I/O Exception: " + iex);
                return false;
            }
        }
        return true;
    }
    
    /**
     * @param outputFileName
     * Quits the program and writes the current houseList into outputFileName
     */
    public void exit(String outputFileName)
    {
        File file = new File(outputFileName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (House obj : houseList)
            {
                bw.write(obj.getHouseNo()+"\n");
                bw.write(obj.getBaseVol()+"\n");
                bw.write(obj.getDailyRead()+"\n");
            }   
        } catch (IOException ie)
        {
            System.out.println("IO Exception " + ie);
        } finally {
            try
            {
                if (bw != null)
                {
                    bw.close();
                }
                if (fw != null)
                {
                    fw.close();
                }
            } catch (IOException iex)
            {
                System.out.println("IO Exception " + iex);
            }
        }
        System.out.println("Thank You for Using this Application!!!!");
        System.exit(1);
    }
    
    /**
     * @param Yearly radon level
     * @return String that contains remediation information
     */
    private String getRating(double radonLevel)
    {
        if (radonLevel < 200)
        {
            return "Safe – no remediation required";
        } else 
        {
            if (radonLevel < 800)
            {
                return "Hazardous – remediation required";
            } else
            {
                return "Dangerous – immediate remediation required";
            }
        }    
    }
    
    /**
     * @param lotNumber
     * function that inserts two objects into the houseList
     */
    private void insertInfill(int lotNumber)
    {
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < houseList.size(); i++)
        {
            if (houseList.get(i).getHouseNo() < lotNumber)
            {
                index1 = i;
            } else {
                index2 = i;
                break;
            }
        }
        double radonLevel = 0.0;
        if ((index1 != -1) && (index2 != -1))
        {
            radonLevel = radonLevel + houseList.get(index1).getDailyRead();
            radonLevel = radonLevel + houseList.get(index2).getDailyRead();
            radonLevel = radonLevel / 2;
        }
    }
    
    /**
     * Function that puts infill in the houseList
     * @param lotNumber
     */
    public void putInfill(int lotNumber)
    {
        // Check for available spaces with lotNumber and lotNumber + 2
        if (!(this.searchHouseNumber(lotNumber) == -1 && 
              this.searchHouseNumber(lotNumber+2) == -1))
        {
            System.out.println("Lot is already occupied");
            return;
        }
        System.out.println("Adding infill");
        this.insertInfill(lotNumber);                  
    }
    
    /**
     * Print Remediation Report
     */
    public void printReport()
    {
        System.out.println("Printing Remediation Report");
        System.out.println("House Address\t\tLevel\t\tRating");
        for (House obj : houseList)
        {
            double yearlyLevel = obj.calcYearlyLevel();
            System.out.println(obj.getHouseNo() + "  " + obj.getStreet() +
                               "\t" + yearlyLevel + "\t" + this.getRating(yearlyLevel));
        }
    }
}
