package com.example.school_app;

public class ParentRegister {

    private String _ParentName;
    private String _ParentPassword;
    private String _ParentSSN;
    private String _ParentPhone;

    public ParentRegister (){

    }

    public String get_ParentPassword() {
        return _ParentPassword;
    }

    public void set_ParentPassword(String _ParentPassword) {
        this._ParentPassword = _ParentPassword;
    }


    public String get_ParentName() {
        return _ParentName;
    }

    public void set_ParentName(String _ParentName) {
        this._ParentName = _ParentName;
    }

    public String get_ParentSSN() {
        return _ParentSSN;
    }

    public void set_ParentSSN(String _ParentSSN) {
        this._ParentSSN = _ParentSSN;
    }

    public String get_ParentPhone() {
        return _ParentPhone;
    }

    public void set_ParentPhone(String _ParentPhone) {
        this._ParentPhone = _ParentPhone;
    }
}
