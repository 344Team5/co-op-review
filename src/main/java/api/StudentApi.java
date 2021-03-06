package api;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    // TODO: figure out what to do for case where uid, name, and admin are not all provided
    public static Object addStudent(Request request, Response response) {
        // INSERT INTO users (uid, name) VALUES (...,...) <-must parse JSON
        Object result = "";
        Map<String,String> dataMap = getAjaxData(request);
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("INSERT INTO users (uid, name, admin) VALUES (?,?,CAST(? AS boolean));");
                st.setString(1, dataMap.get("uid"));
                st.setString(2, dataMap.get("name"));
                st.setString(3, dataMap.get("admin"));
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
                st = db().prepareStatement("DELETE FROM users WHERE uid = ?;");
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

    public static Object getCoops(Request request, Response response) {
        Object result = getStudent(request,response);
        if (result instanceof JSONArray) {
            JSONObject resultJson = new JSONObject();
            resultJson.put("student", result);

            Connection c = db();
            PreparedStatement st = null;
            if (c != null) {
                try {
                    st = db().prepareStatement("SELECT * FROM coops WHERE student_uid = ?;");
                    st.setString(1, request.params().get(":sid"));
                    ResultSet rs = st.executeQuery();
                    resultJson.put("coops", getSQLQueryResultsJson(rs, "id", "start_date", "end_date", "work_report",
                            "student_eval", "eval_token", "employer_id"));

                    result = resultJson;
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
        }

        return result;
    }

    public static boolean isAdmin(String uid) {
        Boolean result = false;
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT (admin) FROM users WHERE uid = ?");
                st.setString(1,uid);
                ResultSet rs = st.executeQuery();
                rs.next();
                result = rs.getBoolean(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
