/**
 * 
 */
package org.purewidgets.client.widgets;


import java.util.ArrayList;

import org.purewidgets.client.widgets.feedback.InputFeedback;
import org.purewidgets.shared.Log;
import org.purewidgets.shared.events.ActionEvent;
import org.purewidgets.shared.events.InputEvent;
import org.purewidgets.shared.widgets.Upload;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.Timer;


/**
 * The TextBox widget shows a graphical textbox with a caption (and reference code)
 * inside.
 * 
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.instantplaces-GuiTextBox[-disabled]</dt>
 * <dd>the outer element. [-disabled] is applied when the textbox is disabled</dd>
 * 
 * <dt>.instantplaces-GuiTextBox[-disabled] .textbox</dt>
 * <dd>the textbox</dd>
 *
 * <dt>.instantplaces-GuiTextBox[-disabled] .captioncontainer</dt>
 * <dd>the container for the caption and reference code</dd>
 * 
 * <dt>.instantplaces-guiTextBox[-disabled] .captioncontainer .caption</dt>
 * <dd>the label with the caption</dd>
 *
 * <dt>.instantplaces-guiTextBox[-disabled] .captioncontainer .referencecode</dt>
 * <dd>the label with the reference code</dd>
 * 
 * <dt>.instantplaces-GuiTextBox[-disabled] caret</dt>
 * <dd>the label with the blinking caret</dd>
 * </dl> 
 * 
 * @author Jorge C. S. Cardoso
 */
public class GuiUpload extends GuiWidget implements KeyPressHandler, FocusHandler {
	/**
	 * The default style name for the TextBox widget.
	 */
	public static final String DEFAULT_STYLENAME = "instantplaces-GuiTextBox";

	/**
	 * The suffix for the textbox control.
	 */
	public static final String TEXTBOX_STYLENAME_SUFFIX = "textbox";

	/**
	 * The suffix for the caption of the textbox.
	 */
	public static final String CAPTIONCONTAINER_STYLENAME_SUFFIX = "captioncontainer";
	
	/**
	 * The suffix for the caption of the textbox.
	 */
	public static final String CAPTIONCONTAINERCAPTION_STYLENAME_SUFFIX = "caption";
	
	/**
	 * The suffix for the caption of the textbox.
	 */
	public static final String CAPTIONCONTAINERREFERENCECODE_STYLENAME_SUFFIX = "referencecode";

	/**
	 * The suffix for the caret.
	 */
	public static final String CARET_STYLENAME_SUFFIX = "caret";
	
	
	/**
	 * The graphical textbox.
	 */
	com.google.gwt.user.client.ui.TextBox textBox;
	
	HTMLPanel htmlPanel;
	Label lblFlashingCaret;
	private HorizontalPanel captionContainer;
	private Label lblCaption;
	private Label lblReferenceCode;
	
	
	Timer caretTimer;
	boolean isCaretOn;
	String caretOn = "|";
	String caretOff = "";
	
	@SuppressWarnings("unused")
	private org.purewidgets.shared.widgets.Upload widgetTextBox;
	
	/**
	 *  The input text
	 */
	private String text = "";
	
	public GuiUpload(String caption) {
		this((String)null, caption);
	}
	
	public GuiUpload(String widgetID, String caption) {
		this(widgetID, caption, null);
	}
		
	public GuiUpload(String widgetId, String caption, String suggestedReference) {
		this(new org.purewidgets.shared.widgets.Upload(widgetId, caption), suggestedReference);
		
	}
	
	public GuiUpload(Upload widgetTextBox, String suggestedReference) {
		super();
		
		this.widgetTextBox = widgetTextBox;
		this.setWidget(widgetTextBox);
		//this.sendToServer();
		
		
	    /*
	     * Gui stuff
	     */
		textBox = new com.google.gwt.user.client.ui.TextBox();
		
		textBox.addKeyPressHandler(this);
		textBox.addFocusHandler(this);
		//textBox.setText(caption);
		
		//setLblCaption( new Label( this.widgetTextBox.getCaption() ) );
		
		
		
		lblReferenceCode = new Label( ReferenceCodeFormatter.format( this.getWidgetOptions().get(0).getReferenceCode() ) );
		
		lblFlashingCaret = new Label("I");
		
		captionContainer = new HorizontalPanel();
	//	captionContainer.add(this.getLblCaption());
		captionContainer.add(this.lblReferenceCode);
		
		htmlPanel = new HTMLPanel("<div id='textbox'></div><div id='caret'></div><div id='caption'></div>");
		
		
		htmlPanel.addAndReplaceElement(textBox, "textbox");
		htmlPanel.addAndReplaceElement(captionContainer, "caption");
		//htmlPanel.addAndReplaceElement(lblReferenceCode, "referencecode");
		htmlPanel.addAndReplaceElement(lblFlashingCaret, "caret");
		
		//setInputFeedbackDisplay(new InputFeedbackPanel());
		initWidget(htmlPanel);
		
	    // Give the overall composite a style name.
	    setStyleName(DEFAULT_STYLENAME);
		this.textBox.addStyleName(GuiUpload.TEXTBOX_STYLENAME_SUFFIX);
		this.captionContainer.addStyleName(GuiUpload.CAPTIONCONTAINER_STYLENAME_SUFFIX);
		this.lblFlashingCaret.addStyleName(GuiUpload.CARET_STYLENAME_SUFFIX);
		this.lblReferenceCode.addStyleName(GuiUpload.CAPTIONCONTAINERREFERENCECODE_STYLENAME_SUFFIX);
		//this.getLblCaption().addStyleName(GuiUpload.CAPTIONCONTAINERCAPTION_STYLENAME_SUFFIX);
		
		
		this.sendToServer();
	}
	
		
	

	@Override
	public void onKeyPress(KeyPressEvent event) {
		if (event.getCharCode() == 13) {
			Log.debug(this, "Enter detected");
			
			ArrayList<String> params = new ArrayList<String>();
			params.add(this.textBox.getText());
			
			InputEvent e = new InputEvent(this.getWidgetOptions().get(0), params);
			textBox.setText("");
			
			// remove the focus so that the internal caret disappears.
			textBox.setFocus(false);
			
			ArrayList<InputEvent> inputList = new ArrayList<InputEvent>();
			inputList.add(e);
			this.widget.onInput(inputList);
			//this.onInput(inputList);	
		}
		
	}
	
	@Override
	public void onReferenceCodesUpdated() {
		if (!(this.lblReferenceCode == null)) {
			this.lblReferenceCode.setText( ReferenceCodeFormatter.format( this.getWidgetOptions().get(0).getReferenceCode() ) );
		}
	}
	
	@Override
	public InputFeedback<GuiUpload> handleInput(InputEvent ie) {
		InputFeedback<GuiUpload> feedback = new InputFeedback<GuiUpload>(ie);
		if ( null != ie.getParameters() && ie.getParameters().size() > 0 ) {
			feedback.setType(InputFeedback.Type.ACCEPTED);
			ActionEvent<GuiUpload> ae = new ActionEvent<GuiUpload>(
					this, // source widget 
					ie, // input event
					ie.getParameters().get(0));
			feedback.setActionEvent(ae);	
			feedback.setInfo(ie.getParameters().get(0));
		} else {
			feedback.setType(InputFeedback.Type.NOT_ACCEPTED);
		}
		
		
		return feedback;
	}		


	@Override
	public void onFocus(FocusEvent event) {
		//this.textBox.setFocus(false);
		
	}
	
	
	public void setText(String text) {
		this.textBox.setText(text);
		this.text = text;
	}
	

	
	@Override
	public void setEnabled(boolean enabled) {
		Log.debug("" + inputEnabled);
		textBox.setEnabled(enabled);
		//setCaret(enabled);
		super.setEnabled(enabled);
	}
	
	@Override
	public void setInputEnabled(boolean inputEnabled) {
		super.setInputEnabled(inputEnabled);
	}
	/*
	@Override
	public void onInput(InputEvent ie) {
		
		InputFeedback f = new InputFeedback(ie);
	
		this.getFeedbackSequencer().add(f);
		
	}
	
	@Override
	public void start(InputFeedback inputFeedback) {
		Log.debug("");
		
		// Disable the caret when showing feedback
		this.setCaret(false);
		
		InputFeedback.Type type;
		InputEvent ie = inputFeedback.getInputEvent();
		String text = "";

		if (ie.getCommand() != null && ie.getCommand().getParameter(0) != null) {
			type = this.isInputEnabled()?InputFeedback.Type.ACCEPTED:InputFeedback.Type.NOT_ACCEPTED;
			text = ie.getCommand().getParameter(0);
			if (type == InputFeedback.Type.ACCEPTED) {
				inputFeedback.setInfo(ie.getIdentity().getName()+": "+text);
			} else {
				inputFeedback.setInfo(ie.getIdentity().getName());
			}
		} else {
			type = InputFeedback.Type.NOT_ACCEPTED;
			if (this.isInputEnabled()) { // not accepted because of parameter error
				inputFeedback.setInfo(ie.getIdentity().getName() + ": " + constants.textboxEmptyTextError());
			} else {
				inputFeedback.setInfo(ie.getIdentity().getName());
			}
		}
		inputFeedback.setType(type);
		
		this.text = text;
	
		this.getInputFeedbackDisplay().show(inputFeedback);

		// Only fire application event if the input was accepted
		if (inputFeedback.getType() == InputFeedback.Type.ACCEPTED) {
			ActionEvent ae = new ActionEvent(ie.getIdentity(), this, ie.getWidgetOptionID(), text);
			this.fireActionEvent(ae);
		}
	}
	
@Override
public void stop(InputFeedback feedback, boolean noMore) {
	super.stop(feedback, noMore);
	
	// if there's no more feedback waiting, turn the caret on
	if (noMore) {
		this.setCaret(this.isInputEnabled());
	}
}
*/

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}


	/**
	 * @param lblCaption the lblCaption to set
	 */
	public void setLblCaption(Label lblCaption) {
		this.lblCaption = lblCaption;
	}


	/**
	 * @return the lblCaption
	 */
	public Label getLblCaption() {
		return lblCaption;
	}



}