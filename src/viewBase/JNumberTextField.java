package viewBase;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class JNumberTextField extends JTextField {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JNumberTextField() {
        super();
        setDocument(new PlainDocument() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void setDocumentFilter(DocumentFilter filter) {
                if (filter instanceof NumberFilter) {
                    super.setDocumentFilter(filter);
                }
            }
        });
        ((PlainDocument) getDocument()).setDocumentFilter(new NumberFilter());
    }

    private class NumberFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
