# Created by zhouwang on 2018/6/8.

from .base import BaseRequestHandler, permission
import datetime
import tornado


class Handler(BaseRequestHandler):
    @permission()
    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def get(self):
        response_data = yield tornado.gen.Task(self._summary)
        self._write(response_data)

    @tornado.gen.coroutine
    def _summary(self):
        now_str_time = datetime.datetime.now().strftime('%Y-%m-%d %H:%M')
        self.cursor.execute(self.select_sql, now_str_time)
        results = self.cursor.dictfetchall()
        return dict(code=200, msg='Query Successful', data=results)

    select_sql = '''
      SELECT * FROM
        (SELECT count(id) as logfile_count FROM logfile) t1,
        (SELECT count(*) as host_count FROM (SELECT count(id) FROM logfile_host GROUP BY host) t2) t3,
        (SELECT count(id) as monitor_item_count FROM monitor_item) t4,
        (SELECT count(id) as user_count FROM user) t5,
        (SELECT count(DISTINCT user_id) as online_user_count FROM session WHERE expire_time>%s) t6
    '''
