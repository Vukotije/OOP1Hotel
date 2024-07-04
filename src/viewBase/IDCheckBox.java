package viewBase;

import javax.swing.JCheckBox;

public class IDCheckBox extends JCheckBox {
	private static final long serialVersionUID = 1L;
	private String id;

    public IDCheckBox(String id, String text) {
        super(text);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
