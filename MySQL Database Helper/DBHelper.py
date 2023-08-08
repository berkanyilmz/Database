import mysql.connector
from mysql.connector import Error

class DBHelper:

    def __init__(self, user, password, database, host='localhost'):
        self.host = host
        self.user = user
        self.password = password
        self.database = database

    def getConnection(self):
        conn = None
        try:
            conn = mysql.connector.connect(
                host=self.host,
                user=self.user,
                password=self.password,
                database=self.database)

            if conn.is_connected():
                print('Connection is successfull')

        except Error as e:
            print(e)

        return conn

    def query(self, connection):
        cursor = connection.cursor()
        SQL = "SELECT ID, Name,CountryCode, District, Population FROM city"
        try:
            cursor.execute(SQL)
            results = cursor.fetchall()

            for result in results:
                print(result)

            cursor.close()
        except Error as e:
            print('Error : ' + e)


    def insert(self, connection, countryName, countryCode, district, population):
        cursor = connection.cursor()
        SQL = "INSERT INTO city (Name, CountryCode, District, Population) VALUES (%s, %s, %s, %s)"
        values = (countryName, countryCode, district, population)

        try:
            cursor.execute(SQL, values)
            connection.commit()
            cursor.close()
            print("Insert was successful")
        except Error as e:
            print(e)


    def where(self, connection, population):
        cursor = connection.cursor()
        SQL = "SELECT * FROM city WHERE Population > %s"
        val = (population, )
        cursor.execute(SQL, val)
        results = cursor.fetchall()

        for result in results:
            print(result)

        cursor.close()


    def orderby(self, connection):
        cursor = connection.cursor()
        SQL = "SELECT * FROM city ORDER BY Population"
        cursor.execute(SQL)

        results = cursor.fetchall()
        for result in results:
            print(result)

        cursor.close()


    def delete(self, connection, countryName):
        cursor = connection.cursor()
        SQL = "DELETE FROM city WHERE Name = %s"
        city = (countryName, )

        try:
            cursor.execute(SQL, city)
            connection.commit()
            print("record(s) deleted")
        except Error as e:
            print(e)
        finally:
            cursor.close()


    def update(self, connection, population, countryName):
        cursor = connection.cursor()
        SQL = "UPDATE city SET Population = %s WHERE Name = %s"
        val = (population, countryName)

        try:
            cursor.execute(SQL, val)
            connection.commit()
            print("record updated")
        except Error as e:
            print(e)
        finally:
            cursor.close()