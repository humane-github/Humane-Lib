package jp.co.humane.dbutil.sample;

import java.util.List;

import jp.co.humane.dbutil.commonsIF.DBAccessor;
import jp.co.humane.dbutil.commonsIF.Database;


public class SampleApp
{
	public void load()
	{
		// datasource.xmlに定義したデータベースから、接続するDBを取得

		try
		{
			Database.login("localhost","InfoClerk","postgres","root");
			DBAccessor ac = Database.getAccessor();

			
		} catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		//
		try
		//try(DBAccessor ac = new DBAccessor())
		{
			DBAccessor ac  = new DBAccessor();
			ac.beginTransaction();
			//Object res = ac.executeQuery("select ST_AsText(poly) as poly from polygons", null, new MapListHandler());
			//List<BlockBean> res = ac.executeQuery("select *  from block where model_id=(select max(model_id) from block)", null,BlockBean.class);
			List<BlockBean> res = ac.executeQuery("select *  from block where model_id=573", null,BlockBean.class);
			List<MaskBean> res2 = ac.executeQuery("select *  from mask", null,MaskBean.class);

			for( BlockBean o : res )
			{
				System.out.println("--------------------------------------------");
				System.out.println("name="+o.getName());
				System.out.println("maskType="+o.getMask_type());
			}

			for( MaskBean m : res2 )
			{
				System.out.println("--------------------------------------------");
				System.out.println("name="+m.getPytype());
			}

		//} catch (FatalException | WarningException | ErrorException e) {
		//	e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public static void main(String[] args )
	{
		new SampleApp().load();
	}


}
