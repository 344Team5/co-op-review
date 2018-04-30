package api;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CoopApi extends DatabaseApi {
    public static Object getCoops(Request request, Response response) {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM coops;");
                result = getSQLQueryResultsJson(rs, "id", "start_date", "end_date", "work_report",
                        "student_eval", "eval_token", "student_uid", "employer_id");
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

    public static Object addCoop(Request request, Response response) {
        Object result = "";
        JSONObject data = new JSONObject(request.body());
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("INSERT INTO coops (start_date, end_date, student_uid, employer_id) VALUES (?,?,?,?);");
                st.setDate(1, Date.valueOf(data.getString("start_date")));
                st.setDate(2, Date.valueOf(data.getString("end_date")));
                st.setString(3, data.getString("student_uid"));
                st.setInt(4, data.getInt("employer_id"));
                st.execute();
                result = "success";
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

    public static Object getCoop(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT * FROM coops where id = CAST(? AS INTEGER);");
                st.setString(1, request.params().get(":cid"));
                ResultSet rs = st.executeQuery();
                result = getSQLQueryResultsJson(rs, "id", "start_date", "end_date", "work_report",
                        "student_eval", "eval_token", "student_uid", "employer_id");
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

    public static Object putCoop(Request request, Response response) {
        return "Create/Update a Student";
    }

    // TODO: check if new values to foreign keys exist in original table before modifying coop
    public static Object patchCoop(Request request, Response response) {
        Object result = "";
        JSONObject data = new JSONObject(request.body());
        int cid = Integer.parseInt(request.params().get(":cid"));
        Map<String, Object> dataMap = data.toMap();
        for (Map.Entry<String,Object> entry : dataMap.entrySet() ) {
            Connection c = db();
            PreparedStatement st = null;
            if (c != null) {
                try {
                    if ( entry.getKey().equals("work_report")) {
                        st = c.prepareStatement("UPDATE coops SET work_report = ? WHERE id = ?");
                        st.setString(1, entry.getValue().toString());
                        st.setInt(2, cid);
                        st.execute();
                        result = "success";
                    } else if (entry.getKey().equals("student_eval")) {
                        st = c.prepareStatement("UPDATE coops SET student_eval = ? WHERE id = ?");
                        st.setString(1, entry.getValue().toString());
                        st.setInt(2, cid);
                        st.execute();
                        result = "success";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
        /*
        Map<String,String> dataMap = getAjaxData(request);

        if (dataMap != null) {
            for (String attributeKey : dataMap.keySet()) {
                Connection c = db();
                PreparedStatement st = null;
                if (c != null) {
                    try {
                        // this looks bad and I should probably refactor it
                        if (attributeKey.equals("work_report") || attributeKey.equals("student_eval") ||
                                attributeKey.equals("eval_token") || attributeKey.equals("student_uid")) {
                            st = db().prepareStatement("UPDATE coops SET "
                                    + attributeKey + " = ? WHERE id = CAST(? AS INTEGER);");
                        } else if (attributeKey.equals("start_date") || attributeKey.equals("end_date")) {
                            st = db().prepareStatement("UPDATE coops SET "
                                    + attributeKey + " = CAST(? AS DATE) WHERE id = CAST(? AS INTEGER);");
                        } else if (attributeKey.equals("employer_id")) {
                            st = db().prepareStatement("UPDATE coops SET "
                                    + attributeKey + " = CAST(? AS INTEGER) WHERE id = CAST(? AS INTEGER);");
                        }
                        if (st != null) {
                            st.setString(1, dataMap.get(attributeKey));
                            st.setString(2, request.params().get(":cid"));
                            //System.out.println(st);
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
        */

    }

    public static Object deleteCoop(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("DELETE FROM coops WHERE id = CAST(? AS INTEGER);");
                st.setString(1, request.params().get(":cid"));
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

    public static Object handleStudentEval(Request request, Response response) {
        return null;
    }
}
