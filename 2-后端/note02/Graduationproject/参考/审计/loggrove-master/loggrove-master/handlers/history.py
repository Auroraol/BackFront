# Created by zhouwang on 2018/6/8.

from .base import BaseRequestHandler, permission

class Handler(BaseRequestHandler):
    @permission()
    def get(self):
        response_data = self._query()
        self._write(response_data)

    def _query(self):
        self.cursor.execute(self.select_sql, self.requser['id'])
        results = self.cursor.dictfetchall()
        return dict(code=200, msg='Query Successful', data=results)

    select_sql = '''
      SELECT
        uri,
        method,
        date_format(record_time, "%%Y-%%m-%%d %%H:%%i:%%s") as record_time
      FROM
        auditlog
      WHERE 
        user_id=%s
      ORDER BY -record_time 
      LIMIT 100                 
    '''