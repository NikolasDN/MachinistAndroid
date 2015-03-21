package com.machinist;

public class Node {
	public int X;
    public int Y;
    public int Rotation;
    public String NodeTypeName;
    public int NodeAddress1;
    public int NodeAddress2;
    public int NodeNr1;
    public int NodeNr2;
    
    // voor gewone wissels en enkele kruiswissels
    public boolean Rechtdoor;
    // voor driewegwissels en dubbele kruiswissels
    public int Stand;
}
