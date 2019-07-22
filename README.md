# GroceryApplication
 Grocery application to manage inventory and purchase
 
 Customer : abtract class with getDiscount() method
 --> RegularCustomer
 --> SeniorCitizen
 --> Employee
CustomerManager: Singleton class used to create new/delete customers, get Customer details from Backend.

Inventory: Singleton class to manage all Inventory details like Items, ItemTypes, earningsPerDay, availableStock
{
	Items : class for Item objects. 
	ItemType: class for ItemType objects.
}

DiscountManager : Singleton class used to get discount based on Customer type, Item and ItemType

Logger: Singleton class for logging input output to System.in. 

Payment: 
-->CashPayment
-->CardPayment

IPaymentProcessor: interface to initiate paymens. 

Transaction: 
-->CashTransaction
-->CardTransaction

Register: similar to supermarket counters in real world. Used to place order, Payment, check the Transaction and give the bill to customer. 
RegisterHandler: Singleton class to allocate available Register from common pool of registers and release them back to the pool once transaction is done. 

TaxFinder: Singleton class to find tax based on itemtype. 

Backend: Backend can be anything- cloud service or on premise webserver. 
DB: showcased to how data flows from client to DB. 

Utils, DateUtils --> utility classes.

Driver: class with main method which simulates from customer entering the grocery, purchase and payment

Credentials: class used to store account name and password of an Employee.
Authenticator: to authenticate a credentials. Not implemented. Just showcased it. 

 Refer: input-output log lines.txt for the working application.