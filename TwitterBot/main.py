import time
import requests
import tweepy

api_key = "ZenKEBmHTl3kNFUL2aAslUCbo"
api_secret = "QXr8RxD7xdJ3vOYbsm7tdpZOaayEUo4NCvq2qsZXfMW38egItr"
bearer_token = r"AAAAAAAAAAAAAAAAAAAAAK9SrQEAAAAAZ9xtZRpoRwGWzdS%2B1jzJ8f2slOY%3D5jNNZa93hw4OftqLMitwLb25EUanpxSYRor8gy6Dp6nP9lZ6oM"
access_token = "2501415992-PjPqAoOSMP6i76kjp6t1aHmRnfTgaBBUJ9Uts12"
access_token_secret = "QmzxOEEzZZhf5D6SoFGSQS7AE8ELDe7T8Lj191dJfdH6v"

client = tweepy.Client(bearer_token, api_key, api_secret, access_token, access_token_secret)
auth = tweepy.OAuth1UserHandler(api_key, api_secret, access_token, access_token_secret)
api = tweepy.API(auth)

# client.create_tweet(text="Tweet from pc")

EVERY_10_MIN = 1 * 60 * 10
EVERY_5_MIN = 1 * 60 * 5


def postToTwitter(allContent: list):
    print("Posting started at ", time.strftime('%X %x %Z'))
    cnt = 1
    for i in allContent:
        try:
            print("\n{} news posted at {}".format(cnt, time.strftime('%X %x %Z')))
            cnt = cnt + 1
            print(i)
            client.create_tweet(text=i)
            print("-------------POSTED----------")
            time.sleep(EVERY_10_MIN)
        except Exception as e:
            print(e)
    time.sleep(EVERY_5_MIN)
    fetchNews("News fetched at {}".format(time.strftime('%X %x %Z')))


def fetchNews(name):
    print(f'Hi, {name}')
    allContent = []
    content: str
    try:
        response = requests.get("http://localhost:9090/news")
        if response.status_code == 200:
            api_res = response.json()
            newsList = api_res["response"]
            for i in newsList:
                try:
                    title = i["title"]
                    url = i["url"]
                    content = i["description"]
                    textMsg = content.split(".")
                    content = ""
                    lengthOfCharacters = len(title)
                    totalCharLeft = 250 - lengthOfCharacters
                    for j in range(0, len(textMsg)):
                        # post only 5 lines
                        word = textMsg[j].strip()
                        if j > 2:
                            break
                        if len(word) > 5:
                            content = content + word + ".\n"
                    if len(content) < totalCharLeft:
                        twitterMsg = title + "\n" + content + "\n" + url
                    else:
                        twitterMsg = title + "\n" + content[0:totalCharLeft] + "...\n" + url
                    allContent.append(twitterMsg)
                except Exception as e:
                    print(e)
        print("fetched content size ", len(allContent))
    except Exception as e:
        print("Service Unavailable! Server will start soon!\n {}".format(e))
        time.sleep(180)
        fetchNews("News fetched at {}".format(time.strftime('%X %x %Z')))
    postToTwitter(allContent)


if __name__ == '__main__':
    fetchNews("News fetched at {}".format(time.strftime('%X %x %Z')))
