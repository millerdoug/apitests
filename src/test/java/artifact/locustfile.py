from locust import HttpUser, task

#Bonus points, as this works :)
class PerfTestOfThisEndpoint(HttpUser):
    @task
    def hello_world(self):
        self.client.get("/comments")
        self.client.post("/comments")

""" Some tool considerations:
It uses a front end for indicating how many users, threads, base url, etc, that in some other tools I've used can be saved in the script or piped in when run, like an environment variable in Java. However, I did notice after reading about it that there probably are command line tools, which is a benefit. This means a performance test might be done in bitbucket/jenkins/etc where even if not run every merge like automated tests, the performance test might be able to be built with essentially single button kick off
If so, then I do like the lightweightness of locust. I always preferred jmeter over proprietary tools like NeoLoad because it was lighter weight and open source, with resources highly available in the market, but I think locust is even more lightweight, which is nice, and there are plenty of python developers, AFAIK.
I also appreciate the fact that you can code anything you want in python. A tool like Jmeter had a wonky partial javascript thing that made it harder to do custom actions. I'm not an expert in python but I know it's a fully featured language with lots of documents/stackoverflow and other information.
I never took advantage of jmeter's grid capability, but looking at the docs, it appears locust also has this, which is probably something I'd learn to use.
Overall, if the system or a microservice is written in python, I'd probably just think about using this alongside of it as it appears to be fully featured for the most common features. I have a bias to keep the number and amount of coding languages to a minimum unless there's a feature or ability that you just can't do with the current one.

Some performance test considerations and challenges:
Aside from speed of transaction and systems falling over and stopping, performance tests should look for other issues. One of the issues I worked on was that more then 1 order got the same order ID at (relatively) higher transaction times. This was due to some SQL code not being thread safe. Another one was when working in AWS message queues sometimes the same message was picked up by multiple workers so there were duplicate transactions. These are harder issues to find and usually very subtle
Careful selection of load is important. In one experience, there was a steady load of 10,000 transactions a minute, but overnight there might be large batch drops which really increased load. So understanding what your load is and where it's coming from is important, so that you can use realistic loads. And, if you're developing a new feature, maybe that will increase the use of an already used feature?
Lastly, one solution I'd do is to implement careful monitoring. This is in addition to something like cloudwatch alarms for failing systems. For example, if there are consistently X amount of incoming transactions, I'd expect there to be X amount of outgoing transactions, within some tolerance. This might indicate a functional issue. Watching a queue size if you're system is built with those, so if the number of 'transactions to process' keeps increasing, this might be an early warning that the system is not properly handling the load. Or, in one of the above cases, looking at transaction IDs and ensuring they are unique
"""
