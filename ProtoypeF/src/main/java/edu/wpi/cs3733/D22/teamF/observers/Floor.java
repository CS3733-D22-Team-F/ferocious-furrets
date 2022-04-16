package edu.wpi.cs3733.D22.teamF.observers;

public enum Floor {
    LL2,LL1,FL1,FL2,FL3,FL4,FL5;

    public Floor next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
    }
    public Floor prev() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() - 1];
    }
}
