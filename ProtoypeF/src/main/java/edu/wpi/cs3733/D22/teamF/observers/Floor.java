package edu.wpi.cs3733.D22.teamF.observers;

public enum Floor {
  LL2,
  LL1,
  FL1,
  FL2,
  FL3,
  FL4,
  FL5;

  public Floor next() {
    // No bounds checking required here, because the last instance overrides
    Floor retVal;
    switch (this) {
      case LL2:
        retVal = LL1;
        break;
      case LL1:
        retVal = FL1;
        break;
      case FL1:
        retVal = FL2;
        break;
      case FL2:
        retVal = FL3;
        break;
      case FL3:
        retVal = FL4;
        break;
      case FL4:
        retVal = FL5;
        break;
      case FL5:
        retVal = FL5;
        break;
      default:
        retVal = FL5;
    }
    return retVal;
  }

  public Floor prev() {
    // No bounds checking required here, because the last instance overrides
    //    return values()[ordinal() - 1];
    Floor retVal;
    switch (this) {
      case LL2:
        retVal = LL2;
        break;
      case LL1:
        retVal = LL2;
        break;
      case FL1:
        retVal = LL1;
        break;
      case FL2:
        retVal = FL1;
        break;
      case FL3:
        retVal = FL2;
        break;
      case FL4:
        retVal = FL3;
        break;
      case FL5:
        retVal = FL4;
        break;
      default:
        retVal = LL2;
    }
    return retVal;
  }

  public int toInt() {
    int retVal;
    switch (this) {
      case LL2:
        retVal = 0;
        break;
      case LL1:
        retVal = 1;
        break;
      case FL1:
        retVal = 2;
        break;
      case FL2:
        retVal = 3;
        break;
      case FL3:
        retVal = 4;
        break;
      case FL4:
        retVal = 5;
        break;
      case FL5:
        retVal = 6;
        break;
      default:
        retVal = -1;
    }
    return retVal;
  }

  public String toFloorString() {
    String retVal = "";
    switch (this) {
      case LL2:
        retVal = "L2";
        break;
      case LL1:
        retVal = "L1";
        break;
      case FL1:
        retVal = "01";
        break;
      case FL2:
        retVal = "02";
        break;
      case FL3:
        retVal = "03";
        break;
      case FL4:
        retVal = "04";
        break;
      case FL5:
        retVal = "05";
        break;
      default:
        retVal = "Undefined";
    }
    return retVal;
  }

  public Floor toFloorEnum(String toConvert) {
    Floor retVal = Floor.FL3; // IDK just need something quick
    switch (toConvert) {
      case "L2":
        retVal = Floor.LL2;
        break;
      case "L1":
        retVal = Floor.LL1;
        break;
      case "FL1":
        retVal = Floor.FL1;
        break;
      case "FL2":
        retVal = Floor.FL2;
        break;
      case "FL3":
        retVal = Floor.FL3;
        break;
      case "FL4":
        retVal = Floor.FL4;
        break;
      case "FL5":
        retVal = Floor.FL5;
        break;
      default:
        retVal = Floor.FL3;
        break;
    }
    return retVal;
  }
}
