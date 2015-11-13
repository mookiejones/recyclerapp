package android.com.solutions.nerd.testapp;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public interface ITextQueryListener {
    void OnTextChanged(String queryText);

    boolean UsesQueryText();
}
