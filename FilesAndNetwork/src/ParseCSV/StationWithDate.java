package ParseCSV;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class StationWithDate implements Comparable {
    private String name;
    private Date openingDate;

    public StationWithDate(String name, Date openingDate){
        this.name = name;
        this.openingDate = openingDate;
    }


    public String toString() {
        return name + " " + (new SimpleDateFormat("dd.MM.yyyy").format(openingDate));
    }

    public String getName() {
        return name;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationWithDate that = (StationWithDate) o;
        return Objects.equals(name, that.name) && Objects.equals(openingDate, that.openingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, openingDate);
    }

    @Override
    public int compareTo(Object o) {
        return ((StationWithDate)o).getOpeningDate().before(this.openingDate) ? 1 : -1;
    }

}
