package api;

import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.Map;

public class StudentApi extends DatabaseApi {

    public static Object getStudents(Request request, Response response) {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE admin = FALSE;");
                result = getSQLQueryResultsJson(rs, "uid", "name");
                response.type("application/json");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static Object addStudent(Request request, Response response) {
        return "Add a Student"; // INSERT INTO users (uid, name) VALUES (...,...) <-must parse JSON
    }

    public static Object getStudent(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT * FROM users WHERE uid = ? AND admin = FALSE;");
                st.setString(1, request.params().get(":sid"));
                ResultSet rs = st.executeQuery();
                result = getSQLQueryResultsJson(rs, "uid", "name");
                response.type("application/json");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static Object putStudent(Request request, Response response) {
        return "Create/Update a Student"; //Implement this only if we need to
    }

    public static Object patchStudent(Request request, Response response) {
        Object result = "";
        Map<String,String> queryMap = getQueryParameters(request, null,
                new String[]{"name", "admin", "uid"});
        if (queryMap != null) {
            for (String attributeKey : queryMap.keySet()) {
                Connection c = db();
                PreparedStatement st = null;
                if (c != null) {
                    try {
                        // this looks bad and I should probably refactor it
                        if (attributeKey.equals("name") || attributeKey.equals("uid")) {
                            st = db().prepareStatement("UPDATE users SET " + attributeKey + " = ? WHERE uid = ?;");
                        } else if (attributeKey.equals("admin")) {
                            st = db().prepareStatement("UPDATE users SET "
                                    + attributeKey + " = CAST(? AS boolean) WHERE uid = ?;");
                        }
                        if (st != null) {
                            st.setString(1, queryMap.get(attributeKey));
                            st.setString(2, request.params().get(":sid"));
                            System.out.println(st);
                            boolean success = st.execute();
                            //result = getSQLQueryResultsJson(rs, "uid", "name");
                            response.type("application/json");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (st != null) {
                            try {
                                st.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static Object deleteStudent(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("DELETE FROM students WHERE uid = ?;");
                st.setString(1, request.params().get(":sid"));
                st.execute();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
