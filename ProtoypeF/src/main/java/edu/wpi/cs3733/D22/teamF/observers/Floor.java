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
    return values()[ordinal() + 1];
  }

  public Floor prev() {
    // No bounds checking required here, because the last instance overrides
    return values()[ordinal() - 1];
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
}
