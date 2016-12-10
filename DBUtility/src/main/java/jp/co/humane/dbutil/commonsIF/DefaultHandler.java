package jp.co.humane.dbutil.commonsIF;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;

/**
 * ResultSetをそのまま返すハンドラー
 * **/
public class DefaultHandler implements ResultSetHandler
{

    /**
     * The RowProcessor implementation to use when converting rows
     * into Object[]s.
     */
	protected RowProcessor convert = BasicRowProcessor.instance();

    /**
     * Creates a new instance of ArrayListHandler using a
     * <code>BasicRowProcessor</code> for conversions.
     */
    public DefaultHandler() {
        super();
    }

    /**
     * Creates a new instance of ArrayListHandler.
     *
     * @param convert The <code>RowProcessor</code> implementation
     * to use when converting rows into Object[]s.
     */
    public DefaultHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

    public Object handle(ResultSet rs) throws SQLException
	{
        return rs;
    }

}
