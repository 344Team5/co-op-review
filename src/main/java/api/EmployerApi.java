package api;

import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.Map;

public class EmployerApi extends DatabaseApi {

    public static Object getEmployers(Request request, Response response) {
        Object result = "";
        Connection c = db();
        Statement st = null;
        if (c != null) {
            try {
                st = db().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM employers;");
                result = getSQLQueryResultsJson(rs, "id", "name", "avg_salary", "reviews");
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

    // currently just needs name and average salary
    public static Object addEmployer(Request request, Response response) {
        Object result = "";
        Map<String,String> dataMap = getAjaxData(request);
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("INSERT INTO employers (name, avg_salary) VALUES (?,CAST(? AS INTEGER));");
                st.setString(1, dataMap.get("name"));
                st.setString(2, dataMap.get("avg_salary"));
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

    public static Object getEmployer(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("SELECT * FROM employers WHERE id = CAST(? AS INTEGER);");
                st.setString(1, request.params().get(":eid"));
                ResultSet rs = st.executeQuery();
                result = getSQLQueryResultsJson(rs, "reviews", "name", "avg_salary", "id");
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

    public static Object putEmployer(Request request, Response response) {
        return "Create/Update an Employer";
    }

    public static Object patchEmployer(Request request, Response response) {
        Object result = "";
        Map<String,String> dataMap = getAjaxData(request);

        if (dataMap != null) {
            for (String attributeKey : dataMap.keySet()) {
                Connection c = db();
                PreparedStatement st = null;
                if (c != null) {
                    try {
                        // this looks bad and I should probably refactor it
                        if (attributeKey.equals("name") || attributeKey.equals("id")) {
                            st = db().prepareStatement("UPDATE employers SET "
                                    + attributeKey + " = ? WHERE id = CAST(? AS INTEGER);");
                        } else if (attributeKey.equals("reviews")) {
//                            // get current reviews and append new one to them
//                            PreparedStatement tempSt = db().prepareStatement("SELECT reviews FROM employers where id = CAST(? AS INTEGER);");
//                            ResultSet tempRs = tempSt.executeQuery();
//                            Object currentReviews = getSQLQueryResultsJson(tempRs);
//                            String reviews = currentReviews.toString() + queryMap.get(attributeKey);
//                            queryMap.put(attributeKey, reviews);

                            st = db().prepareStatement("UPDATE employers SET "
                                    + attributeKey + " = CAST(? AS text[]) WHERE id = CAST(? AS INTEGER);");
                        } else if (attributeKey.equals("avg_salary")) {
                            st = db().prepareStatement("UPDATE employers SET "
                                    + attributeKey + " = CAST(? AS INTEGER) WHERE id = CAST(? AS INTEGER);");
                        }
                        if (st != null) {
                            st.setString(1, dataMap.get(attributeKey));
                            st.setString(2, request.params().get(":eid"));
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

        return result;
    }

    public static Object deleteEmployer(Request request, Response response) {
        Object result = "";
        Connection c = db();
        PreparedStatement st = null;
        if (c != null) {
            try {
                st = db().prepareStatement("DELETE FROM employers WHERE id = CAST(? AS INTEGER);");
                st.setString(1, request.params().get(":eid"));
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
