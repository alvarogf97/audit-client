package com.example.alvaro.client_audit.activities;
import com.example.alvaro.client_audit.core.entities.Argument;

public interface DialogActivity {

    Argument getSelected_Argument();
    void hide_dialog();
    void setSelected_Argument(Argument selected_argument);
    void showDialog();

}
