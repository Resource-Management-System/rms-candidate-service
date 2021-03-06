package callum.uni.project.rms.candidate.model;

public enum BaseLocation {
    HOLBORN("Holborn", 51.51786134247904, -0.10656187900410206),
    ASTON("Aston", 52.49616146003411, -1.8848329963332042),
    TREFOREST("Treforest", 51.57610719378798, -3.2963061139058754),
    WOKING("Woking", 51.319125999480754, -0.5632602646266073),
    SALE("Sale", 53.42806463009481, -2.3228197028682076),
    TOLTEC("Toltec", 51.54062452745604, -2.5816538633643695),
    GLASGOW("Glasgow", 55.85782304234286, -4.260820309650051),
    INVERNESS("Inverness", 57.4813896083084, -4.182960955776178),
    LIVERPOOL("Liverpool", 53.407734200192515, -2.991518969210855),
    CANARY_WHARF("Canary Wharf", 51.5039834492962, -0.017974261520652023),
    SHEFFIELD("Sheffield", 53.40746768901701, -1.4157324534592846),
    ROTHERHAM("Rotherham", 53.449003880289325, -1.3273036582715554),
    NAIRN("Nairn", 57.58413112986287, -3.846210802183585),
    TELFORD("Telford", 52.67616528824928, -2.4427836926202633);

    private String label;

    private double x;

    private double y;

    BaseLocation(String label, double x, double y){
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public String getLabel(){
        return this.label;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }
}
