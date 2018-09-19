-- 传感器数据
-- 数据库存储方案说明：
-- 	数据库分为三类集合(Collection)，
-- 	第一类UsersCollection用来存储用户注册时的基本信息，作为用户信息库；
-- 	第二类SensorPointCollection用来存储其网关在UsersCollection中文档的ObjectID——gatewayObjID，和sensorPointID；
-- 	第三类SensorCollectio用来其sensorPointID在SensorPointCollection中文档的ObjectID——sensorPointObjID，以及传感器收集到的数据，
-- 	包括传感器的sensorID来区分传感器种类，传感器数据sensorData和时间（年月日时分秒）；


