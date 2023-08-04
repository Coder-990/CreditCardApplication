package hr.rba.creditcardapplication.util;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DIRECTORY_NAME = "file";
    public static final String UNABLE_TO_CREATE_DIRECTORY = "Unable to create directory! ";
    public static final String OLD_FILE_IS_DELETED = "Oib matches and old file is deleted, and replaced with new one of status 'INACTIVE'";
    public static final String DELETING_NON_EXISTING_PERSON = "Error in file writer while deleting!";
    public static final String ERROR_MSG_FILE_WRITING = "Error in file writer! ";
    public static final String ERROR_MSG_FILE_EXISTS = "Error, file already exists!";
    public static final String SUCESS_MSG_FILE_WRITTEN = "Person was successfully fetched and written to a file";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String EMPTY_LIST = "Person list is empty, store some data!";
    public static final String INITIALIZED_SUCCESSFULLY = "initialized successfully";
    public static final String UPDATED_SUCCESSFULLY = "updated successfully";
    public static final String STORED_PERMANENTLY = "stored permanently";
    public static final String PERSON = "Person ";
    public static final String DELETED_SUCCESSFULLY = "deleted successfully";
    public static final String SUCCESSFULLY_BY_OIB = "fetched successfully by oib";
    public static final String PERSONS = "Persons ";
    public static final String GET_ALL = "all";
    public static final String GET_BY = "getBy/";
    public static final String STORE = "store";
    public static final String UPDATE_BY = "updateBy/";
    public static final String REMOVE_BY = "removeBy/";
    public static final String OIB = "{oib}";
    public static final String ID = "{id}";
}
